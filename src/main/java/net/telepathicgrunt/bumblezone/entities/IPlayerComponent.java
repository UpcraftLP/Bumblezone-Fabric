package net.telepathicgrunt.bumblezone.entities;

import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.world.dimension.DimensionType;

public interface IPlayerComponent extends Component {
    boolean getIsTeleporting();
    void setIsTeleporting(boolean isTeleporting);
    DimensionType getNonBZDimension();
    void setNonBZDimension(DimensionType nonBZDimension);
}