package telegramBot.controllers.filesController.implementations.parseFiles.userDto;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.controllers.CommunicationMode;
import telegramBot.models.UserDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseFilesUserDTO {

    public void parseUserDTO(Update update, UserDTO user, String path) {
        long id = update.hasMessage() ? update.getMessage().getFrom().getId() : update.getCallbackQuery().getFrom().getId();
        try {
            Files.writeString(Path.of(path + id + "/" + user.getNickName() + ".txt"),
                    "Name☼" + user.getNickName() + "\n"
                            + "ID☼" + user.getId() + "\n"
                            + "CommunicationMode☼" + user.getCommunicationMode() + "\n"
                            + "SelfName☼" + user.getSelfName() + "\n"
                            + "DateOfBirthday☼" + user.getDateOfBirthday() + "\n"
                            + "GMT☼" + user.getGmt() + "\n"
                            + "AboutSelf☼" + user.getText() + "\n"
                            + "listPersonIn☼" + parseListPerson(user.getListPersonIn()) + "\n"
                            + "listPersonOut☼" + parseListPerson(user.getListPersonOut()) + "\n"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO unparseFileUserDTO(Update update, String path) {
        long id = update.hasMessage() ? update.getMessage().getFrom().getId() : update.getCallbackQuery().getFrom().getId();
        String name = update.hasMessage() ? update.getMessage().getFrom().getUserName() : update.getCallbackQuery().getFrom().getUserName();
        UserDTO userDTO = new UserDTO();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(path + id + "/" + name + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, String> entityMap = new HashMap<>();
        String[] parts = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("☼")) {
                parts[i] = lines.get(i);
                String[] download = parts[i].split("☼");
                entityMap.put(download[0], download[1]);
            } else {
                String text = entityMap.get("AboutSelf");
                String newText = text + "\n" + lines.get(i);
                entityMap.put("AboutSelf", newText);
            }
        }
        userDTO.setNickName(entityMap.get("Name"));
        userDTO.setId(Long.parseLong(entityMap.get("ID")));
        userDTO.setCommunicationMode(CommunicationMode.valueOf(entityMap.get("CommunicationMode")));
        userDTO.setSelfName(entityMap.get("SelfName"));
        userDTO.setDateOfBirthday(entityMap.get("DateOfBirthday"));
        userDTO.setListPersonIn(unparseListPerson(entityMap.get("listPersonIn")));
        userDTO.setListPersonOut(unparseListPerson(entityMap.get("listPersonOut")));
        userDTO.setText(entityMap.get("AboutSelf"));
        userDTO.setGmt(Integer.parseInt(entityMap.get("GMT")));
        return userDTO;
    }

    private String parseListPerson(List<Long> listPerson) {
        if (listPerson != null) {
            String persons = null;
            for (Long person : listPerson) {
                persons = persons + person + ",";
            }
            return persons;
        } else return null;

    }

    private List<Long> unparseListPerson(String list) {
        if (!list.equals("null")) {
            List<Long> listPerson = new ArrayList<>();
            String[] array = list.split(",");
            for (String s : array) listPerson.add(Long.valueOf(s));
            return listPerson;
        } else return null;

    }
}
