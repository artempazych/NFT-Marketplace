package ui.components;

import model.Marketplace;
import model.Nft;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// NftComponent class is the abstract class that describes the ui of the Nft class
public abstract class NftComponent extends JPanel implements ActionListener {
    JLabel image;
    JLabel name;
    JLabel price;
    JButton button;
    Marketplace marketplace;
    User user;
    Nft nft;
    JLabel balanceLabel;

    // MODIFIES: this
    // REQUIRES: constructs NftComponent JPanel element
    public NftComponent(Marketplace marketplace, User user, JLabel balanceLabel, Nft nft) {
        this.balanceLabel = balanceLabel;
        this.marketplace = marketplace;
        this.user = user;
        this.nft = nft;

        initNftComponentJPanel();
        addScaledImage(nft);
        addNameLabel(nft);
        addPriceLabel(nft);

        this.add(this.image);
        this.add(this.name);
        this.add(this.price);
    }

    // MODIFIES: this
    // REQUIRES: initialized the price element
    private void addPriceLabel(Nft nft) {
        this.price = new JLabel("Price: " + nft.getPrice() + " USDT");
        this.price.setHorizontalAlignment(JLabel.CENTER);
        this.price.setBounds(0, 225, 200, 25);
    }

    // MODIFIES: this
    // REQUIRES: initialized the name element
    private void addNameLabel(Nft nft) {
        this.name = new JLabel("Name: " + nft.getName());
        this.name.setHorizontalAlignment(JLabel.CENTER);
        this.name.setBounds(0, 200, 200, 25);
    }

    // MODIFIES: this
    // REQUIRES: adds image to the component
    private void addScaledImage(Nft nft) {
        this.image = new JLabel();
        ImageIcon image = new ImageIcon(nft.getFilePath());
        Image scaleImage = image.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        this.image.setIcon(new ImageIcon(scaleImage));
        this.image.setBounds(0,0,200, 200);
    }

    // MODIFIES: this
    // REQUIRES: initialized the NftComponent JPanel
    private void initNftComponentJPanel() {
        this.setPreferredSize(new Dimension(200, 300));
        this.setLayout(null);
        this.setBackground(Color.white);
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

    public Nft getNft() {
        return this.nft;
    }
}
