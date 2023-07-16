package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarketplaceTest {
    Marketplace marketplace;
    Nft nft1;
    Nft nft2;

    @BeforeEach
    public void setup(){
        marketplace = new Marketplace();
        nft1 = new Nft("nft1", "filepath", 100);
        nft2 = new Nft("nft2", "filepath", 200);
    }

    @Test
    public void constructorTest(){
        assertEquals(0, marketplace.getNfts().size());
    }

    @Test
    public void addNftTest(){
        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());
    }

    @Test
    public void addNftAlreadyInTheListTest(){
        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());

        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());
    }

    @Test
    public void addNftMultipleTest(){
        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());

        marketplace.addNft(nft2);
        assertTrue(marketplace.getNfts().contains(nft2));
        assertEquals(2, marketplace.getNfts().size());
    }

    @Test
    public void removeNftTest(){
        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());

        marketplace.removeNft(nft1);
        assertFalse(marketplace.getNfts().contains(nft1));
        assertEquals(0, marketplace.getNfts().size());
    }

    @Test
    public void removeNftNotInTheListTest(){
        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());

        marketplace.removeNft(nft2);
        assertFalse(marketplace.getNfts().contains(nft2));
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());
    }

    @Test
    public void removeNftMultipleTest(){
        marketplace.addNft(nft1);
        assertTrue(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());

        marketplace.addNft(nft2);
        assertTrue(marketplace.getNfts().contains(nft2));
        assertEquals(2, marketplace.getNfts().size());

        marketplace.removeNft(nft1);
        assertFalse(marketplace.getNfts().contains(nft1));
        assertEquals(1, marketplace.getNfts().size());

        marketplace.removeNft(nft2);
        assertFalse(marketplace.getNfts().contains(nft2));
        assertEquals(0, marketplace.getNfts().size());

        List<Event> logs = new ArrayList<Event>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            logs.add(next);
        }

        assertEquals(logs.get(0).getDescription(), "Nft " + nft1.getName() + " added to Marketplace");
        assertEquals(logs.get(1).getDescription(), "Nft " + nft2.getName() + " added to Marketplace");
        assertEquals(logs.get(2).getDescription(), "Nft " + nft1.getName() + " removed from Marketplace");
        assertEquals(logs.get(3).getDescription(), "Nft " + nft2.getName() + " removed from Marketplace");
    }
}
