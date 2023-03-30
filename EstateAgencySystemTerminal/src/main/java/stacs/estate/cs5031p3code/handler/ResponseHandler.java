package stacs.estate.cs5031p3code.handler;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * A class which only contains static methods to interpret response from the server.
 *
 * @author 190005675
 */
public class ResponseHandler {
    /**
     * Parse the response as the message it contains as no data is expected.
     */
    public static String parseVoid(Map<?, ?> response) {
        // No data, the only important thing is the message
        if (response != null) {
            return (String) response.get("message");
        }
        return null;
    }

    /**
     * Parse the response as the user key (JSON web token) if the HTTP status code is ok.
     */
    public static String parseUserKey(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                Map<?, ?> data = (Map<?, ?>) response.get("data");
                if (data != null) {
                    return (String) data.get("user_key");
                }
            }
        }
        return null;
    }

    /**
     * Parse the response as a user if the HTTP status code is ok.
     */
    public static String parseUser(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                Map<?, ?> data = (Map<?, ?>) response.get("data");
                if (data != null) {
                    Integer id = (Integer) data.get("userId");
                    String email = (String) data.get("userEmail");
                    String name = (String) data.get("userName");
                    String phone = (String) data.get("userPhone");
                    String address = (String) data.get("userAddress");
                    return String.format("UserID: %s%nEmail: %s%nName: %s%nPhone: %s%nAddress: %s%n",
                            id, email, name, phone, address);
                }
            }
            return (String) response.get("message");
        }
        return null;
    }

    /**
     * Parse the response as a list of users if the HTTP status code is ok.
     */
    public static String parseListUser(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
                if (data != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Object obj : data) {
                        Map<?, ?> user = (Map<?, ?>) obj;
                        Integer id = (Integer) user.get("userId");
                        String email = (String) user.get("userEmail");
                        String name = (String) user.get("userName");
                        String phone = (String) user.get("userPhone");
                        String address = (String) user.get("userAddress");
                        String userString = String.format("UserID: %s%nEmail: %s%nName: %s%nPhone: %s%nAddress: %s%n",
                                id, email, name, phone, address);
                        sb.append(userString);
                        sb.append("\n");
                    }
                    return sb.toString();
                }
            }
            return (String) response.get("message");
        }
        return null;
    }

    /**
     * Parse the response as a list of buildings if the HTTP status code is ok.
     */
    public static String parseListBuilding(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
                if (data != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Object obj : data) {
                        Map<?, ?> building = (Map<?, ?>) obj;
                        Integer id = (Integer) building.get("buildingId");
                        String name = (String) building.get("buildingName");
                        String address = (String) building.get("buildingAddress");
                        String buildingString = String.format("BuildingID: %s%nBuilding Name: %s%nBuilding Address: %s%n",
                                id, name, address);
                        sb.append(buildingString);
                        sb.append("\n");
                    }
                    return sb.toString();
                }
            }
            return (String) response.get("message");
        }
        return null;
    }

    /**
     * Parse the response as a list of flats if the HTTP status code is ok.
     */
    public static String parseListFlat(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
                if (data != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Object obj : data) {
                        Map<?, ?> flat = (Map<?, ?>) obj;
                        Integer flatId = (Integer) flat.get("flatId");
                        String name = (String) flat.get("flatName");
                        Double area = (Double) flat.get("flatArea");
                        String date = (String) flat.get("flatSoldOutDate");
                        Double price = (Double) flat.get("flatPrice");
                        Integer buildingId = (Integer) flat.get("buildingId");
                        Integer userId = (Integer) flat.get("userId");
                        String flatString = String.format(
                                "FlatID: %s%nName: %s%nArea: %s%nSold Date: %s%nPrice: %s%nBuildingID: %s%nUserID: %s%n",
                                flatId, name, area, date, price, buildingId, userId);
                        sb.append(flatString);
                        sb.append("\n");
                    }
                    return sb.toString();
                }
            }
            return (String) response.get("message");
        }
        return null;
    }

    /**
     * Parse the response as a list of roles if the HTTP status code is ok.
     */
    public static String parseListRole(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
                if (data != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Object obj : data) {
                        Map<?, ?> role = (Map<?, ?>) obj;
                        Integer id = (Integer) role.get("roleId");
                        String name = (String) role.get("roleName");
                        String key = (String) role.get("roleKey");
                        String roleString = String.format("RoleID: %s%nRole Name: %s%nRole Key: %s%n",
                                id, name, key);
                        sb.append(roleString);
                        sb.append("\n");
                    }
                    return sb.toString();
                }
            }
            return (String) response.get("message");
        }
        return null;
    }
}
