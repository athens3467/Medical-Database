public class CSVUtil {
    public static String[] parseLine(String line) {
        if (line == null) {
            return new String[0];
        }

        int fieldCount = 1;
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);

            if (current == '"') {
                if (inQuotes && i + 1 < line.length() &&
                        line.charAt(i + 1) == '"') {
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (current == ',' && !inQuotes) {
                fieldCount++;
            }
        }

        if (inQuotes) {
            return new String[0];
        }

        String[] fields = new String[fieldCount];
        StringBuilder field = new StringBuilder();

        int index = 0;
        inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);

            if (current == '"') {
                if (inQuotes && i + 1 < line.length() &&
                        line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (current == ',' && !inQuotes) {
                fields[index] = field.toString().trim();
                index++;
                field.setLength(0);
            } else {
                field.append(current);
            }
        }

        fields[index] = field.toString().trim();

        return fields;
    }

    public static String quote(String value) {
        if (value == null) {
            return "";
        }

        if (value.contains(",") || value.contains("\"") ||
                value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }

    public static Identity parseIdentityName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid patient name.");
        }

        String value = name.trim();

        if (value.contains(",")) {
            String[] parts = value.split(",", 2);

            if (parts[0].trim().isEmpty() ||
                    parts[1].trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid patient name.");
            }

            return new Identity(parts[1].trim(), parts[0].trim());
        }

        String[] parts = value.split("\\s+", 2);

        if (parts.length != 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid patient name.");
        }

        return new Identity(parts[0], parts[1]);
    }
}
