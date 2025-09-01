public class Volume implements Module {
    private double volume;
    private String unit;
    private boolean valid =false;
    public Volume(String volumeCode, String unitCode) {
        if (volumeCode.length() == 5 && unitCode.matches("[123]")) {//for check matches of number we use matches
            int intPart = Integer.parseInt(volumeCode.substring(0, 3));
            int decPart = Integer.parseInt(volumeCode.substring(3));
            volume = intPart + decPart / 100.0;

            switch (unitCode) {
                case "1":
                    unit = "ml";
                break;
                case "2":
                    unit = "litre";
                    break;
                case "3":
                    unit = "pint";
                    break;
                default:
                    return;
            }
            valid = true;
        }
    }
    @Override
    public boolean isValid() {
        return valid;
    }
    public double getVolume() {
        return volume;
    }

    public String getUnit() {
        return unit;
    }
}
