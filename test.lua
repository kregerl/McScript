libs = ...
local logger = require("com.loucaskreger.mcscript.api.lua.client.LuaLogger")

logger.info("this is a test")
print('EventHandler', EventHandler)
print('Client', Player)

function printTable(table)
    for k, v in pairs(table) do
        print("Key:", k, "Value:", v)
    end
end

for k, v in pairs(Player) do
    print("Key:", k, "Value:", v)
end

function test (event)
    if not Player.isNil() then
        printTable(Player.getInventory().getArmor)
        printTable(Player.getInventory().getItems)
        printTable(Player.getInventory().getOffhand)
        print(Player.getInventory().selectedHotbarIndex)
    end
end

function chatMsg(event)
    print("Chat message in lua:", event:getMessage())
end

local ctresult = EventHandler.addEventListener(EventType.CLIENT_TICK, test)
print('Client Tick Result:', ctresult)
local cmresult = EventHandler.addEventListener(EventType.CHAT_MSG_RECEIVED, chatMsg)
print('Chat Message Result:', cmresult)