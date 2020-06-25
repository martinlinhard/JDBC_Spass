/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.SortOrder;

/**
 *
 * @author martin
 */
public class ColumnWrapper {

    private SortOrder[] orders;

    public ColumnWrapper() {
        this.orders = new SortOrder[]{
            SortOrder.ASC,
            SortOrder.ASC,
            SortOrder.ASC,
            SortOrder.ASC};
    }

    public SortOrder flipAndFetch(int index) {
        this.orders[index] = this.orders[index].flip();
        return this.orders[index];
    }
}
