package ui.components;

import model.Marketplace;
import model.Nft;
import model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

// NftComponentMarketplace class is the class that describes the ui of the Nft class
// that is displayed in the Marketplace
public class NftComponentMarketplace extends NftComponent {

    // MODIFIES: this
    // EFFECTS: constructs NftComponentMarketplace object
    public NftComponentMarketplace(Marketplace marketplace, User user, JLabel balanceLabel, Nft nft) {
        super(marketplace, user, balanceLabel, nft);

        this.button = new JButton("Buy");
        this.button.setBackground(new Color(45, 145, 45));
        this.button.setBounds(10, 250, 180, 30);
        this.button.setFocusable(false);
        this.button.setForeground(Color.WHITE);
        this.button.addActionListener(this);
        this.button.setBorder(new LineBorder(Color.WHITE));

        this.add(this.button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button) {
            this.setVisible(false);
            this.user.buyNFT(nft, this.marketplace);
            JOptionPane.showMessageDialog(null,
                                "Successfully purchased NFT: " + nft.getName()
                                        + "!\nIt's now added to your gallery!",
                                   "Purchase Confirmation",
                                        JOptionPane.INFORMATION_MESSAGE);
            this.balanceLabel.setText("Balance: " + this.user.getBalance());
        }
    }
}
