package com.avianca.rest;


public enum CustomerType {

    Adult("A"), CBBG("B"), Child("C"), EXST("E"), NONAME("G"), Infant("I"), NCPACP("N"), NCP("Z");

    private final String type;

    CustomerType(String _type) {
        this.type = _type;
    }

    public String type() {
        return this.type;
    }

//    public String toString() {
//        return this.type;
//    }

    public static CustomerType fromString(String _type) {
        switch (_type) {
            case "0":
            case "G":
                return NONAME;

            case "A":
                return Adult;

            case "B":
                return CBBG;

            case "C":
                return Child;

            case "E":
                return EXST;

            case "I":
            case "IN":
                return Infant;

            case "N":
                return NCPACP;

            case "Z":
                return NCP;

            default:
                return null;
        }
    }
}
