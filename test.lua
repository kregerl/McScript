libs = ...

print('EventHandler', EventHandler)

function test (event)
    print('phase', event.phase)
    print('side', event.side)
    print('type', event.type)
end

function chatMsg(event)

    print("Chat message in lua:", event.getMessage())
end

local ctresult = EventHandler.addEventListener(EventType.CLIENT_TICK, test)
print('Client Tick Result:', ctresult)
local cmresult = EventHandler.addEventListener(EventType.CHAT_MSG_RECEIVED, chatMsg)
print('Chat Message Result:', cmresult)