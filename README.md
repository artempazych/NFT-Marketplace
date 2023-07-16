# NFT Markeplace

## Description
This project is a simple version of NFT marketplace
In this application users can:
- deposit and withdraw money into their account in USDT
- buy and sell (only previously purchased) NFTs
- view the gallery of Purchased NFTs
- view the marketplace of available NFTs

This project is interesting to me, since I was always wondering how such big online stores
operate so much data and provide so many functionality to it's users. As a vivid example, **Amazon** or
**Facebook Marketplace** made it much easier to shop online and buy whatever you want, providing the
user-friendly interface and a lot of helping features such as comparison. As a simple version, I decided
to develop an NFT Marketplace where user can browse and purchase NFTs.


## User Stories
- As a user, I want to be able to deposit and withdraw USDT to my account
- As a user, I want to be able to select which NFT do I want to purchase
- As a user, I want to be able to sell previously bought NFT 
- As a user, I want to be able to see the gallery of my purchased NFTs
- As a user, I want to be able to view the gallery of available NFTs on the marketplace
- As a user I want to purchase Nft and automatically add it to my gallery and remove it from Marketplace
- As a user I want to sell purchased Nft and automatically remove it from my gallery and add it to Marketplace
- As a user I want to be able to save the current state of the program (Marketplace's and User's states) to the json file
- As a user I want to be able to load the previously saved state of the program (Marketplace's and User's states) from the json file
    

## Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by 
  1. Pressing the Marketplace button 
  2. Press the buy button to purchase any NFT or NFTs
- You can generate the second required event related to adding Xs to a Y by
  1. Pressing the MyGallery button to view all your purchased NFT 
  2. You can also remove them from your gallery by pressing sell button
- All the visual component related to the application are stores inside on the img folder in the ui package. 
  <br /> Images of NFTs are displayed in when pressing either Marketplace or MyGallery button
- You can save the state of my application by pressing the Save Data button
- You can reload the state of my application by the Load Data button


## Phase 4: Task 2

Sample of the logs after running the application:

Wed Nov 30 11:26:53 PST 2022
Nft 0N1 #4111 added to Marketplace

Wed Nov 30 11:26:53 PST 2022
Nft 0N1 #4112 added to Marketplace

Wed Nov 30 11:26:53 PST 2022
Nft 0N1 #4113 added to Marketplace

Wed Nov 30 11:26:53 PST 2022
Nft 0N1 #4114 added to Marketplace

Wed Nov 30 11:26:53 PST 2022
Nft 0N1 #4115 added to Marketplace

Wed Nov 30 11:26:53 PST 2022
Nft 0N1 #4116 added to Marketplace

Wed Nov 30 11:26:55 PST 2022
Nft 0N1 #4112 has been purchased and added to the Gallery

Wed Nov 30 11:26:55 PST 2022
Nft 0N1 #4112 removed from Marketplace

Wed Nov 30 11:26:57 PST 2022
Nft 0N1 #4114 has been purchased and added to the Gallery

Wed Nov 30 11:26:57 PST 2022
Nft 0N1 #4114 removed from Marketplace

Wed Nov 30 11:26:58 PST 2022
Nft 0N1 #4111 has been purchased and added to the Gallery

Wed Nov 30 11:26:58 PST 2022
Nft 0N1 #4111 removed from Marketplace

Wed Nov 30 11:27:00 PST 2022
Nft 0N1 #4114 has been sold and removed from the Gallery

Wed Nov 30 11:27:00 PST 2022
Nft 0N1 #4114 added to Marketplace


## Phase 4: Task 3

If I had more time I would implement this refactoring:
- Implement a Singleton pattern for Marketplace and User, in order 
  to reduce the use of marketplace and user instances in the files and function that operate on
  changing the state of those object. Since there is only one instance of Marketplace and User used
  in the whole application, Singleton pattern would be a good design solution.
- Create a class for the button for the NftComponentMarketplace and NftComponentMyGallery. 
  Since there is many repeated code I could have just refactor that into a class with parameters: text and color.