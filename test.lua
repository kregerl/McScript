libs = ...
local events = require(libs.."events")

print('events', events)

function test (event)
    print("Hello World!")
    print(event)
end

print("Value of: ", events.register("CLIENT_TICK", test))