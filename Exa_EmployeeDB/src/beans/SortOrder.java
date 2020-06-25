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
public enum SortOrder {
    ASC,
    DESC;

    public SortOrder flip() {
        switch (this) {
            case ASC:
                return DESC;
            case DESC:
                return ASC;
        }
        return null;
    }
}
