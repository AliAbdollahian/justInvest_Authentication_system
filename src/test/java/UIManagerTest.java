import org.example.PasswordManager;
import org.example.UIManager;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Scanner;

public class UIManagerTest {

    @Test
    public void testUserLoginPassword() {
        UIManager uiManager = new UIManager();
        String username = "test_user12";
        String correctPassword = "ValidPassword123@";

        String simulatedInput = username + "\n" + correctPassword + "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);
        PasswordManager pm = new PasswordManager();
        pm.addUser(username, correctPassword);
        Map<String, String> user = pm.getUser(username);
        boolean loginResult = uiManager.login(new Scanner(System.in), user);
        assertTrue("Login should succeed with correct password.", loginResult);
    }
}
