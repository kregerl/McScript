# McScript

*Name to be changed

## How it works

McScript provides a platform and set of API's to write scripts in Lua that communicate with Minecraft.

Client side McScript will have the ability to add elements to the hud/screen, listen for events, and modify the player's
state. Client side Modules will be able to be hot reloaded through the Modules' button in the Options menu.

Server side Modules could in some cases be hot reloaded, but for now they won't be as they can access registries to
create new items/blocks. Any changes made to the registries would require a restart of the game. 