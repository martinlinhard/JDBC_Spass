/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author martin
 */
public enum SortType {
    NAME,
    GENDER,
    BIRTHDATE,
    HIREDATE;

    private SortOrder sortOrder;

    public static SortType getInstance(String variant, SortOrder order) {
        SortType type = SortType.valueOf(variant);
        type.sortOrder = order;
        return type;
    }

    @Override
    public String toString() {
        String s = "";
        switch (this) {
            case NAME:
                s = "e.last_name {ORDER}, e.first_name";
                break;
            case GENDER:
                s = "e.gender";
                break;
            case BIRTHDATE:
                s = "e.birth_date";
                break;
            case HIREDATE:
                s = "e.hire_date";
                break;
        }
        s += " {ORDER}";
        return replaceAllOccurences("{ORDER}", this.sortOrder.toString(), s);
    }

    public static String replaceAllOccurences(String to_replace, String replacer, String input) {
        while (input.contains(to_replace)) {
            input = input.replace(to_replace, replacer);
        }
        return input;
    }
}
