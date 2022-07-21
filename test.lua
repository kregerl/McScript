libs = ...
local logger = require("com.loucaskreger.mcscript.api.lua.client.Logger")

logger.info("this is a test")
print('EventHandler', EventHandler)
print('Client', Player)

for k, v in pairs(Player) do
    print("Key:", k, "Value:", v)
end

function test (event)
    if not Player.isNil() then
        Player.setSprinting(true)
--         print('Player exp:', Player.getExperience())
        if Player.isUsingItem() then
            print('Player using item:', Player.isUsingItem())
        end
    end

--     print('phase', event.phase)
--     print('side', event.side)
--     print('type', event.type)
end

function chatMsg(event)
    print("Chat message in lua:", event:getMessage())
end

local ctresult = EventHandler.addEventListener(EventType.CLIENT_TICK, test)
print('Client Tick Result:', ctresult)
local cmresult = EventHandler.addEventListener(EventType.CHAT_MSG_RECEIVED, chatMsg)
print('Chat Message Result:', cmresult)