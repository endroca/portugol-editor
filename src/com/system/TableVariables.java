/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SIMONETO-2
 */
public class TableVariables {

    private JTable table = null;
    private DefaultTableModel model;

    public TableVariables(JTable t) {
        table = t;
        model = (DefaultTableModel) table.getModel();
    }

    public void add(Object name, Object type, Object value) {
        Object[] rowData = {name, type, value};
        model.addRow(rowData);
    }

    public void update(Object name, Object value) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(name)) {
                model.setValueAt(value, i, 2);
            }
        }
    }

}
