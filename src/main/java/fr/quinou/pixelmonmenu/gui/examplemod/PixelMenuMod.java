/*    */ package fr.quinou.pixelmonmenu.gui.examplemod;
/*    */ 
/*    */ import net.minecraft.client.gui.screen.MainMenuScreen;
/*    */ import net.minecraft.client.gui.screen.MultiplayerScreen;
/*    */ import net.minecraftforge.client.event.GuiOpenEvent;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mod("pixelmonmenu")
/*    */ public class PixelMenuMod
/*    */ {
/* 18 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */ 
/*    */   
/*    */   public PixelMenuMod() {
/* 22 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onOpenGui(GuiOpenEvent event) {
/* 27 */     if (event.getGui() != null && (event.getGui().getClass() == MainMenuScreen.class || event.getGui().getClass() == MultiplayerScreen.class))
/* 28 */       event.setGui(new GuiPixelMainMenu()); 
/*    */   }
/*    */ }

