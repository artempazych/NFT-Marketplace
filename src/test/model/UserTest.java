package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User account;
    Marketplace marketplace;

    Nft nft1001;
    Nft nft1000;
    Nft nft100;
    Nft nft900;
    Nft nft350;

    @BeforeEach
    void setup(){
        account = new User();
        marketplace = new Marketplace();

        nft1001 = new Nft("nft", "filepath", 1001);
        nft1000 = new Nft("nft", "filepath", 1000);
        nft100 = new Nft("nft", "filepath", 100);
        nft900 = new Nft("nft", "filepath", 900);
        nft350 = new Nft("nft", "filepath", 350);

        marketplace.addNft(nft1001);
        marketplace.addNft(nft1000);
        marketplace.addNft(nft100);
        marketplace.addNft(nft900);
        marketplace.addNft(nft350);
    }

    @Test
    void constructorTest(){
        assertEquals(0, account.getBalance());
    }

    @Test
    void depositLessThenMinimumAmountTest(){
        assertFalse(account.deposit(10));
        assertEquals(0, account.getBalance());
    }

    @Test
    void depositMinimumAmountTest(){
        boolean depositStatus = account.deposit(100);
        assertTrue(depositStatus);
        assertEquals(100, account.getBalance());
    }

    @Test
    void depositMoreThenMinimumAmountTest(){
        boolean depositStatus = account.deposit(1000);
        assertTrue(depositStatus);
        assertEquals(1000, account.getBalance());
    }

    @Test
    void depositMultipleTest(){
        boolean depositStatus = account.deposit(1000);
        assertTrue(depositStatus);
        assertEquals(1000, account.getBalance());

        depositStatus = account.deposit(1000);
        assertTrue(depositStatus);
        assertEquals(2000, account.getBalance());
    }

    @Test
    void withdrawMoreThenAmountTest(){
        assertFalse(account.withdraw(10));
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void withdrawLessThenBalanceTest(){
        account.deposit(1000);
        assertTrue(account.withdraw(500));
        assertEquals(account.getBalance(), 500);
    }

    @Test
    void withdrawAllTest(){
        account.deposit(1000);
        assertTrue(account.withdraw(1000));
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void buyNftNotEnoughAmountTest(){
        // deposit money
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());
        // check buy nft with price > then balance returns false
        assertFalse(account.buyNFT(nft1001, marketplace));
        // check balance stays the same
        assertEquals(1000, account.getBalance());
        // check nft is not in the gallery (list of nfts)
        assertFalse(account.getGallery().contains(nft1001));
        // check NFT stayed in Marketplace
        assertTrue( marketplace.getNfts().contains(nft1001));

    }

    @Test
    void buyNftExactlyAmountTest(){
        // deposit money
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());
        // check buy nft with price = balance returns true
        assertTrue(account.buyNFT(nft1000, marketplace));
        // check balance becomes 0
        assertEquals(0, account.getBalance());
        // check nft is in the gallery (list of nfts)
        assertTrue(account.getGallery().contains(nft1000));
        // check NFT gone from Marketplace
        assertFalse(marketplace.getNfts().contains(nft1000));
    }

    @Test
    void buyNftMoreThenEnoughAmountTest(){
        // deposit money
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());
        // check buy nft with price < balance returns true
        assertTrue(account.buyNFT(nft100, marketplace));
        // check balance becomes balance-price
        assertEquals(900, account.getBalance());
        // check nft is in the gallery (list of nfts)
        assertTrue(account.getGallery().contains(nft100));
        // check NFT gone from Marketplace
        assertFalse(marketplace.getNfts().contains(nft100));
    }

    @Test
    void buyNftMultipleTest(){
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());

        // check buy nft with price < balance returns true
        assertTrue(account.buyNFT(nft100, marketplace));
        // check balance becomes balance-price
        assertEquals(900, account.getBalance());
        // check nft is in the gallery (list of nfts)
        assertTrue(account.getGallery().contains(nft100));
        // check NFT gone from Marketplace
        assertFalse(marketplace.getNfts().contains(nft100));

        // check buy nft with price = balance returns true
        assertTrue(account.buyNFT(nft900, marketplace));
        // check balance becomes 0
        assertEquals(0, account.getBalance());
        // check nft is in the gallery (list of nfts)
        assertTrue(account.getGallery().contains(nft900));
        // check NFT gone from Marketplace
        assertFalse(marketplace.getNfts().contains(nft900));
    }


    @Test
    void sellNftNotInGalleryTest(){
        // deposit money
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());
        // check that NFT is not in gallery
        assertFalse(account.getGallery().contains(nft100));
        // sell the NFT
        assertFalse(account.sellNFT(nft100, marketplace));
        // check balance stayed the same
        assertEquals(1000, account.getBalance());
        // check gallery size didn't change
        assertEquals(0, account.getGallery().size());
        // check NFT is in Marketplace
        assertTrue(marketplace.getNfts().contains(nft100));
    }

    @Test
    void sellNftInGalleryTest(){
        // deposit money
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());
        // check that NFT is in gallery
        account.buyNFT(nft100, marketplace);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());
        // sell the NFT
        assertTrue(account.sellNFT(nft100, marketplace));
        // check balance increased by the price of NFT
        assertEquals(1000, account.getBalance());
        // check gallery size decreased by 1
        assertEquals(0, account.getGallery().size());
        // check NFT is in Marketplace
        assertTrue(marketplace.getNfts().contains(nft100));
    }

    @Test
    void sellNftMultipleTest(){
        // deposit money
        account.deposit(1000);
        // check that balance has increased by the amount of deposit
        assertEquals(1000, account.getBalance());
        // check that NFT is in gallery

        account.buyNFT(nft100, marketplace);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());
        // check NFT is not in Marketplace
        assertFalse(marketplace.getNfts().contains(nft100));

        account.buyNFT(nft350, marketplace);
        assertTrue(account.getGallery().contains(nft350));
        assertEquals(2, account.getGallery().size());
        /// check NFT is not in Marketplace
        assertFalse(marketplace.getNfts().contains(nft350));

        // sell the NFT
        assertTrue(account.sellNFT(nft100, marketplace));
        // check balance increased by the price of NFT
        assertEquals(1000-350, account.getBalance());
        // check gallery size decreased by 1
        assertEquals(1, account.getGallery().size());
        // check NFT is in Marketplace
        assertTrue(marketplace.getNfts().contains(nft100));

        // sell the NFT
        assertTrue(account.sellNFT(nft350, marketplace));
        // check balance increased by the price of NFT
        assertEquals(1000, account.getBalance());
        // check gallery size decreased by 1
        assertEquals(0, account.getGallery().size());
        // check NFT is in Marketplace
        assertTrue(marketplace.getNfts().contains(nft350));
    }

    @Test
    void addNftOneTest(){
        assertFalse(account.getGallery().contains(nft100));
        assertEquals(0, account.getGallery().size());

        account.addNft(nft100);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());
    }

    @Test
    void addNftAlreadyInTheListTest(){
        assertFalse(account.getGallery().contains(nft100));
        assertEquals(0, account.getGallery().size());

        account.addNft(nft100);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());

        account.addNft(nft100);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());
    }

    @Test
    void addNftMultipleTest(){
        assertFalse(account.getGallery().contains(nft100));
        assertEquals(0, account.getGallery().size());

        account.addNft(nft100);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());

        account.addNft(nft350);
        assertTrue(account.getGallery().contains(nft100));
        assertTrue(account.getGallery().contains(nft350));
        assertEquals(2, account.getGallery().size());
    }

    @Test
    void removeNftNotInTheListTest(){
        assertFalse(account.getGallery().contains(nft100));
        assertEquals(0, account.getGallery().size());

        account.removeNft(nft100);

        assertFalse(account.getGallery().contains(nft100));
        assertEquals(0, account.getGallery().size());
    }

    @Test
    void removeNftOneTest(){
        account.addNft(nft100);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());

        account.removeNft(nft100);

        assertFalse(account.getGallery().contains(nft100));
        assertEquals(0, account.getGallery().size());
    }

    @Test
    void removeNftMultipleTest(){
        account.addNft(nft100);
        assertTrue(account.getGallery().contains(nft100));
        assertEquals(1, account.getGallery().size());

        account.addNft(nft350);
        assertTrue(account.getGallery().contains(nft100));
        assertTrue(account.getGallery().contains(nft350));
        assertEquals(2, account.getGallery().size());

        account.removeNft(nft100);

        assertFalse(account.getGallery().contains(nft100));
        assertTrue(account.getGallery().contains(nft350));
        assertEquals(1, account.getGallery().size());

        account.removeNft(nft350);

        assertFalse(account.getGallery().contains(nft100));
        assertFalse(account.getGallery().contains(nft350));
        assertEquals(0, account.getGallery().size());


        List<Event> logs = new ArrayList<Event>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            logs.add(next);
        }

        // I use indicies from 5 to 8, because the @BeforeEach function adds 5 nfts to the Marketplace first
        assertEquals(logs.get(5).getDescription(), "Nft " + nft100.getName()
                + " has been purchased and added to the Gallery");
        assertEquals(logs.get(6).getDescription(), "Nft " + nft350.getName()
                + " has been purchased and added to the Gallery");
        assertEquals(logs.get(7).getDescription(), "Nft " + nft100.getName()
                + " has been sold and removed from the Gallery");
        assertEquals(logs.get(8).getDescription(), "Nft " + nft350.getName()
                + " has been sold and removed from the Gallery");
    }




}
