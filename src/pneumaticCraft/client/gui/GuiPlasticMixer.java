package pneumaticCraft.client.gui;

import java.awt.Point;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import pneumaticCraft.client.gui.widget.WidgetTank;
import pneumaticCraft.client.gui.widget.WidgetTemperature;
import pneumaticCraft.common.inventory.ContainerPlasticMixer;
import pneumaticCraft.common.tileentity.TileEntityPlasticMixer;
import pneumaticCraft.lib.PneumaticValues;
import pneumaticCraft.lib.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPlasticMixer extends GuiPneumaticContainerBase<TileEntityPlasticMixer>{

    public GuiPlasticMixer(InventoryPlayer player, TileEntityPlasticMixer te){
        super(new ContainerPlasticMixer(player, te), te, Textures.GUI_PLASTIC_MIXER);
    }

    @Override
    public void initGui(){
        super.initGui();

        addWidget(new WidgetTemperature(0, guiLeft + 55, guiTop + 25, 295, 500, te.getLogic(0)));
        addWidget(new WidgetTemperature(1, guiLeft + 82, guiTop + 25, 295, 500, te.getLogic(1), PneumaticValues.PLASTIC_MIXER_MELTING_TEMP));
        addWidget(new WidgetTemperature(2, guiLeft + 128, guiTop + 29, 295, 500, te.getLogic(2), PneumaticValues.PLASTIC_MIXER_MELTING_TEMP));
        addWidget(new WidgetTank(3, guiLeft + 145, guiTop + 14, te.getFluidTank()));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y){
        super.drawGuiContainerForegroundLayer(x, y);

        fontRendererObj.drawString("Upgr.", 15, 19, 4210752);
        fontRendererObj.drawString("Hull", 56, 16, 4210752);
        fontRendererObj.drawString("Item", 88, 16, 4210752);
        fontRendererObj.drawString("Liquid", 131, 5, 4210752);
    }

    @Override
    protected Point getInvNameOffset(){
        return new Point(0, -1);
    }

    @Override
    protected Point getInvTextOffset(){
        return null;
    }

    @Override
    protected void addProblems(List<String> curInfo){
        super.addProblems(curInfo);
        if(te.getFluidTank().getFluidAmount() == 0) {
            if(te.getStackInSlot(4) == null) {
                curInfo.add("gui.tab.problems.plasticMixer.noPlastic");
            } else {
                curInfo.add("gui.tab.problems.notEnoughHeat");
            }
        } else {
            if(te.getStackInSlot(4) != null) {
                if(te.getLogic(1).getTemperature() >= PneumaticValues.PLASTIC_MIXER_MELTING_TEMP && te.getFluidTank().getCapacity() - te.getFluidTank().getFluidAmount() < 1000) {
                    curInfo.add("gui.tab.problems.plasticMixer.plasticLiquidOverflow");
                } else if(te.getLogic(2).getTemperature() < PneumaticValues.PLASTIC_MIXER_MELTING_TEMP) {
                    curInfo.add(te.getStackInSlot(4).stackSize < 64 ? "gui.tab.problems.plasticMixer.plasticOccupied" : "gui.tab.problems.plasticMixer.plasticOverflow");
                }
            } else {
                curInfo.add("gui.tab.problems.plasticMixer.tooMuchHeat");
            }
        }
    }
}
