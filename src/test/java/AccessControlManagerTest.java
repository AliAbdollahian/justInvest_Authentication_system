import org.example.AccessControlManager;
import org.example.Role;
import org.example.RolePermissions;
import org.junit.Test;
import java.util.Set;
import static org.junit.Assert.*;

public class AccessControlManagerTest {
    @Test
    public void testValidateUser_Client() {
        Role role = AccessControlManager.getUserRole("Sasha Kim");
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("CLIENT", role.name()); // Assuming Role is an enum
    }

    @Test
    public void testValidateUser_PremiumClient() {
        Role role = AccessControlManager.getUserRole("Noor Abbasi");
        System.out.println("Role for Noor Abbasi: " + role); // Debugging line

        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("PREMIUM_CLIENT", role.name()); // Assuming Role is an enum
    }


    @Test
    public void testValidateUser_FinancialAdvisor() {
        Role role = AccessControlManager.getUserRole("Mikael Chen");
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("FINANCIAL_ADVISOR", role.name()); // Assuming Role is an enum
    }

    @Test
    public void testValidateUser_Teller() {
        Role role = AccessControlManager.getUserRole("Alex Hayes");
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("TELLER", role.name()); // Assuming Role is an enum
    }

    @Test
    public void testValidateUser_InvalidUser() {
        Role role = AccessControlManager.getUserRole("Invalid User");
        assertNull("Role should be null for an invalid user", role);
    }

    @Test
    public void testValidateUser_CaseInsensitive() {
        Role role = AccessControlManager.getUserRole("sasha kim");
        assertNotNull("Role should not be null for case-insensitive match", role);
        assertEquals("CLIENT", role.name()); // Assuming Role is an enum
    }

    @Test
    public void testGetAuthorizedOperations_ClientRole() {
        Set<Integer> operations = RolePermissions.getAuthorizedOperations(Role.CLIENT);
        assertTrue(operations.contains(1));  // View account balance
        assertTrue(operations.contains(2));  // View investment portfolio
        assertTrue(operations.contains(4));  // View Financial Advisor contact info
    }

    @Test
    public void testGetAuthorizedOperations_PremiumClientRole() {
        Set<Integer> operations = RolePermissions.getAuthorizedOperations(Role.PREMIUM_CLIENT);
        assertTrue(operations.contains(1));  // View account balance
        assertTrue(operations.contains(2));  // View investment portfolio
        assertTrue(operations.contains(3));  // Modify investment portfolio
        assertTrue(operations.contains(5));  // View Financial Planner contact info
    }

    @Test
    public void testGetAuthorizedOperations_TellerRole() {
        Set<Integer> operations = RolePermissions.getAuthorizedOperations(Role.TELLER);
        assertTrue(operations.contains(8));  // Access system during business hours
    }

    @Test
    public void testGetAuthorizedOperations_EmployeeRole() {
        Set<Integer> operations = RolePermissions.getAuthorizedOperations(Role.EMPLOYEE);
        assertTrue(operations.contains(1));  // View account balance
        assertTrue(operations.contains(2));  // View investment portfolio
    }
}

