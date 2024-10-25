import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Inventory Item class
class InventoryItem {
    private String name;
    private int quantity;
    private double price;

    public InventoryItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Name: " + name + ", Quantity: " + quantity + ", Price: " + price;
    }
}

public class InventoryManagementSystem extends JFrame {
    private ArrayList<InventoryItem> inventoryList = new ArrayList<>();
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, quantityField, priceField;

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Name", "Quantity", "Price"}, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(inventoryTable);

        // Form setup
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add components to main frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
    }

    // Method to add an item
    private void addItem() {
        String name = nameField.getText();
        int quantity;
        double price;

        try {
            quantity = Integer.parseInt(quantityField.getText());
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity or price format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        InventoryItem newItem = new InventoryItem(name, quantity, price);
        inventoryList.add(newItem);
        tableModel.addRow(new Object[]{name, quantity, price});
        clearFields();
    }

    // Method to update an item
    private void updateItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = nameField.getText();
            int quantity;
            double price;

            try {
                quantity = Integer.parseInt(quantityField.getText());
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity or price format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            InventoryItem item = inventoryList.get(selectedRow);
            item.setName(name);
            item.setQuantity(quantity);
            item.setPrice(price);

            tableModel.setValueAt(name, selectedRow, 0);
            tableModel.setValueAt(quantity, selectedRow, 1);
            tableModel.setValueAt(price, selectedRow, 2);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to delete an item
    private void deleteItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            inventoryList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Clear input fields
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryManagementSystem().setVisible(true);
            }
        });
    }
}
