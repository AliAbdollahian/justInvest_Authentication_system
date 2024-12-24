# Password Security and Access Control

## Access Control Model
**Chosen Model:** RBAC (Role-Based Access Control)
- Permissions are assigned based on roles.
- Users are assigned roles, determining their permissions.

## Password Hashing
**Algorithm:** Argon2id
- Resistant to side-channel and GPU attacks.
- Adjustable memory, iterations, and parallelism for security.
- Example record: `Username:Salt:PasswordHash`

## Weak Password Prevention
**Source:** OWASP 10k Weak Passwords List
- Ensures passwords are not among commonly used weak passwords.

## Test Cases
### Access Control:
1. **Valid User Role Assignment:** Verifies roles are correctly assigned.
2. **Invalid Users:** Ensures invalid usernames are handled properly.
3. **Operation Authorization:** Confirms correct permissions for roles.

### Password Management:
1. **Add User:** Verifies correct username, salt, and hash format.
2. **Retrieve User:** Ensures accurate record retrieval.
3. **Password Verification:** Validates correct passwords against stored hashes.

### Enrollment:
1. **Valid Enrollment:** Checks role and password validation.
2. **Invalid Role/Password:** Ensures proper prompts for corrections.

### Login:
1. **Successful Login:** Validates correct username/password grants access.
