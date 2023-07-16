package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NftTest {

    Nft nft;

    @Test
    void constructorNameFilePathPriceTest(){
        nft = new Nft("nft", "nfts/nft.jpg", 100);
        assertEquals("nft", nft.getName());
        assertEquals("nfts/nft.jpg", nft.getFilePath());
        assertEquals(100, nft.getPrice());
        assertEquals("", nft.getDescription());
    }

    @Test
    void constructorNameFilePathPriceDescriptionTest(){
        nft = new Nft("nft", "nfts/nft.jpg", 10, "description");
        assertEquals("nft", nft.getName());
        assertEquals("nfts/nft.jpg", nft.getFilePath());
        assertEquals(10, nft.getPrice());
        assertEquals("description", nft.getDescription());
    }


}
