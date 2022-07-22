
function onClientTick(event)
    if not Player.isNil() then
        if Player.isWalking() then
            Player.setSprinting(true)
        end
    end
end

EventHandler.addEventListener(EventType.CLIENT_TICK, onClientTick)