package net.mc3699.arcc.capabilities;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<IPeripheral> PERIPHERAL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
}
