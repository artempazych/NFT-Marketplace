package ui.components;

import model.Marketplace;
import model.Nft;
import model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

// NftComponentMarketplace class is the class that describes the ui of the Nft class
// that is displayed in MyGallery
public class NftComponentMyGallery extends NftComponent {

    // MODIFIES: this
    // EFFECTS: constructs NftComponentMyGallery object
    public NftComponentMyGallery(Marketplace marketplace, User user, JLabel balanceLabel, Nft nft) {
        super(marketplace, user, balanceLabel, nft);

        this.button = new JButton("Sell");
        this.button.setBackground(new Color(196, 112, 55));
        this.button.setBounds(10, 250, 180, 30);
        this.button.setFocusable(false);
        this.button.setForeground(Color.WHITE);
        this.button.addActionListener(this);
        this.button.setBorder(new LineBorder(Color.WHITE));

        this.add(this.button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == super.button) {
            super.setVisible(false);
            super.user.sellNFT(nft, super.marketplace);
            JOptionPane.showMessageDialog(null,
                                "Successfully sold NFT: " + nft.getName()
                                        + "!\nIt's now back to the marketplace.",
                                   "Sell Confirmation",
                                        JOptionPane.INFORMATION_MESSAGE);
            this.balanceLabel.setText("Balance: " + this.user.getBalance());
        }
    }
}
