import org.example.EnrolUsers;
import org.example.PasswordManager;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class EnrolUsersTest {
    private static final String TEST_ROLES_FILE = "roles.csv";
    private EnrolUsers enrolUsers;
    private PasswordManager passwordManager;

    @Before
    public void setUp() {
        enrolUsers = new EnrolUsers();
        passwordManager = new PasswordManager();
    }

    @Test
    public void testEnrolUser() {
        String simulatedInput = "StrongPassword@123\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        enrolUsers.enrolUser("test_user");
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("User test_user enrolled successfully with role: FINANCIAL_ADVISOR"));

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_ROLES_FILE))) {
            String line;
            boolean userExists = false;
               reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.equals("test_user,FINANCIAL_ADVISOR")) {
                    userExists = true;
                    break;
                }
            }
            assertTrue("The roles file should contain the newly enrolled user.", userExists);
        } catch (IOException e) {
            fail("Error reading the roles file: " + e.getMessage());
        }
    }



    @Test
    public void testEnrolUserInvalidPassword() {
        // Simulate invalid and valid password input
        String simulatedInput = "weak\nStrongPassword@123\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        enrolUsers.enrolUser("test_user");

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Password must be at least 8 characters long."));
        assertTrue(consoleOutput.contains("User test_user enrolled successfully with role: FINANCIAL_ADVISOR"));
    }

    @Test
    public void testEnrolUserInvalidRole() {
        String simulatedInput = "StrongPassword@123\n99\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        enrolUsers.enrolUser("test_user");

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Invalid role selection. Please try again."));
    }
}

