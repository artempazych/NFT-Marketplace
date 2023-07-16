package model;

import org.junit.jupiter.api.Test;
import persistance.MarketplaceJsonReader;
import persistance.MarketplaceJsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source of the code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class MarketplaceJsonWriterTest {
    @Test
    void testUserWriterInvalidFile() {
        try {
            Marketplace marketplace = new Marketplace();
            MarketplaceJsonWriter writer = new MarketplaceJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMarketplace() {
        try {
            Marketplace marketplace = new Marketplace();
            MarketplaceJsonWriter writer = new MarketplaceJsonWriter("./data/testWriterEmptyMarketplace.json");
            writer.open();
            writer.write(marketplace);
            writer.close();

            MarketplaceJsonReader reader = new MarketplaceJsonReader("./data/testWriterEmptyMarketplace.json");
            marketplace = reader.read();
            assertEquals(0, marketplace.getNfts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMarketplace() {
        try {
            Marketplace marketplace = new Marketplace();
            Nft nft1 = new Nft("test1", "filepath/test1.jpg", 100, "");
            Nft nft2 = new Nft("test2", "filepath/test2.jpg", 10, "desc");
            marketplace.addNft(nft1);
            marketplace.addNft(nft2);

            MarketplaceJsonWriter writer = new MarketplaceJsonWriter("./data/testWriterGeneralMarketplace.json");
            writer.open();
            writer.write(marketplace);
            writer.close();

            MarketplaceJsonReader reader = new MarketplaceJsonReader("./data/testWriterGeneralMarketplace.json");
            marketplace = reader.read();
            assertEquals(2, marketplace.getNfts().size());

            Nft nft1test = marketplace.getNfts().get(0);
            Nft nft2test = marketplace.getNfts().get(1);

            assertEquals(nft1test.getName(), nft1.getName());
            assertEquals(nft1test.getPrice(), nft1.getPrice());
            assertEquals(nft1test.getFilePath(), nft1.getFilePath());
            assertEquals(nft1test.getDescription(), nft1.getDescription());

            assertEquals(nft2test.getName(), nft2.getName());
            assertEquals(nft2test.getPrice(), nft2.getPrice());
            assertEquals(nft2test.getFilePath(), nft2.getFilePath());
            assertEquals(nft2test.getDescription(), nft2.getDescription());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
