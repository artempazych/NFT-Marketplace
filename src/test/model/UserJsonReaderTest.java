package model;

import org.junit.jupiter.api.Test;
import persistance.UserJsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source of the code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class UserJsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        UserJsonReader reader = new UserJsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        UserJsonReader reader = new UserJsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals(0, user.getBalance());
            assertEquals(0, user.getGallery().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        UserJsonReader reader = new UserJsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            Nft nft1 = new Nft("test1", "filepath/test1.jpg", 100, "");
            Nft nft2 = new Nft("test2", "filepath/test2.jpg", 10, "desc");

            assertEquals(100, user.getBalance());
            assertEquals(2, user.getGallery().size());

            Nft nft1test = user.getGallery().get(0);
            Nft nft2test = user.getGallery().get(1);

            assertEquals(nft1test.getName(), nft1.getName());
            assertEquals(nft1test.getPrice(), nft1.getPrice());
            assertEquals(nft1test.getFilePath(), nft1.getFilePath());
            assertEquals(nft1test.getDescription(), nft1.getDescription());

            assertEquals(nft2test.getName(), nft2.getName());
            assertEquals(nft2test.getPrice(), nft2.getPrice());
            assertEquals(nft2test.getFilePath(), nft2.getFilePath());
            assertEquals(nft2test.getDescription(), nft2.getDescription());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
