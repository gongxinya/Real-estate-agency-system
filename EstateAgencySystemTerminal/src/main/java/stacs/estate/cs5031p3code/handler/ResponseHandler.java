package stacs.estate.cs5031p3code.handler;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ResponseHandler {
    public static String parseVoid(Map<?, ?> response) {
        // No data, the only important thing is the message
        if (response != null) {
            return (String) response.get("message");
        }
        return null;
    }

    public static String parseUserKey(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                Map<?, ?> data = (Map<?, ?>) response.get("data");
                return (String) data.get("user_key");
            }
        }
        return null;
    }

    public static String parseUser(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                Map<?, ?> data = (Map<?, ?>) response.get("data");
                Integer id = (Integer) data.get("userId");
                String email = (String) data.get("userEmail");
                String name = (String) data.get("userName");
                String phone = (String) data.get("userPhone");
                String address = (String) data.get("userAddress");
                return String.format("UserID: %s%nEmail: %s%nName: %s%nPhone: %s%nAddress: %s%n",
                        id, email, name, phone, address);
            }
            return (String) response.get("message");
        }
        return null;
    }

    public static String parseListUser(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
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
            return (String) response.get("message");
        }
        return null;
    }

    public static String parseListBuilding(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
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
            return (String) response.get("message");
        }
        return null;
    }

    public static String parseListFlat(Map<?, ?> response) {
        if (response != null) {
            Integer code = (Integer) response.get("code");
            if (code.equals(HttpStatus.OK.value())) {
                // contains data
                List<?> data = (List<?>) response.get("data");
                StringBuilder sb = new StringBuilder();
                for (Object obj : data) {
                    Map<?, ?> flat = (Map<?, ?>) obj;
                    Integer flatId = (Integer) flat.get("flatId");
                    String name = (String) flat.get("flatName");
                    String area = (String) flat.get("flatArea");
                    String date = (String) flat.get("flatSoldOutDate");
                    String price = (String) flat.get("flatPrice");
                    Integer buildingId = (Integer) flat.get("buildingId");
                    Integer userId = (Integer) flat.get("userId");
                    String flatString = String.format(
                            "FlatID: %s%nName: %s%nArea: %s%nSold date: %s%nPrice: %s%nBuildingID: %s%nUserID: %s%n",
                            flatId, name, area, date, price, buildingId, userId);
                    sb.append(flatString);
                    sb.append("\n");
                }
                return sb.toString();
            }
            return (String) response.get("message");
        }
        return null;
    }
}
