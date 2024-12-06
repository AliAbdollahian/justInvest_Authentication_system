package org.example;

import java.util.*;

public class RolePermissions {

    private static final Map<Role, Set<Integer>> roleOperationsMap = new HashMap<>();

    static {
        roleOperationsMap.put(Role.CLIENT, Set.of(1, 2, 4));
        roleOperationsMap.put(Role.PREMIUM_CLIENT, Set.of(1, 2, 3, 4, 5));
        roleOperationsMap.put(Role.FINANCIAL_ADVISOR, Set.of(1, 2, 3, 6, 7));
        roleOperationsMap.put(Role.FINANCIAL_PLANNER, Set.of(1, 2, 3, 5, 6, 7));
        roleOperationsMap.put(Role.TELLER, Set.of(8));
        roleOperationsMap.put(Role.EMPLOYEE, Set.of(1, 2));
    }

    public static Set<Integer> getAuthorizedOperations(Role role) {
        return roleOperationsMap.getOrDefault(role, Collections.emptySet());
    }
}
