import org.example.AccessControlManager;
import org.example.Role;
import org.example.RolePermissions;
import org.junit.Test;
import java.util.Set;
import static org.junit.Assert.*;

public class AccessControlManagerTest {
    @Test
    public void testValidateUser_Client() {
        String role = String.valueOf(AccessControlManager.getUserRole("Sasha Kim"));
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("CLIENT", role);
    }

    @Test
    public void testValidateUser_PremiumClient() {
        String role = String.valueOf(AccessControlManager.getUserRole("Noor Abbasi"));
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("PREMIUM_CLIENT", role);
    }

    @Test
    public void testValidateUser_FinancialAdvisor() {
        String role = String.valueOf(AccessControlManager.getUserRole("Mikael Chen"));
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("FINANCIAL_ADVISOR", role);
    }

    @Test
    public void testValidateUser_Teller() {
        String role = String.valueOf(AccessControlManager.getUserRole("Alex Hayes"));
        assertNotNull("Role should not be null for a valid user", role);
        assertEquals("TELLER", role);
    }

    @Test
    public void testValidateUser_InvalidUser() {
        Role role = (AccessControlManager.getUserRole("Invalid User"));
        assertNull("Role should be null for an invalid user", role);
    }

    @Test
    public void testValidateUser_EmptyUsername() {
        Role role = (AccessControlManager.getUserRole(""));
        assertNull("Role should be null for an empty username", role);
    }

    @Test
    public void testValidateUser_CaseInsensitive() {
        String role = String.valueOf(AccessControlManager.getUserRole("sasha kim"));
        assertNotNull("Role should not be null for case-insensitive match", role);
        assertEquals("CLIENT", role);
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

