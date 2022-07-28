Registry.setName("Better Sprinting")
Registry.setAuthor("AreUThreateningMe")
Registry.setDescription("A basic module that makes you move while you're on the ground and walking.")
Registry.setVersion("0.1");


function onClientTick(event)
    if not Player.isNil() then
        if Player.isWalking() then
            Player.setSprinting(true)
        end
    end
end

EventHandler.addEventListener(EventType.CLIENT_TICK, onClientTick)