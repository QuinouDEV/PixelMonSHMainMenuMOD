/*    */ package fr.quinou.pixelmonmenu.gui.pixelmenu;
/*    */ 
/*    */ import com.mojang.blaze3d.matrix.MatrixStack;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.widget.button.Button;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.StringTextComponent;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ 
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class PixelButton
/*    */   extends Button
/*    */ {
/*    */   private ResourceLocation resourceLocation;
/*    */   
/*    */   public PixelButton(int x, int y, int width, int height, ResourceLocation resourceLocation, Button.IPressable p_i51135_11_) {
/* 21 */     super(x, y, width, height, StringTextComponent.EMPTY, p_i51135_11_);
/* 22 */     this.resourceLocation = resourceLocation;
/*    */   }
/*    */   
/*    */   public void setPosition(int x, int y) {
/* 26 */     this.x = x;
/* 27 */     this.y = y;
/*    */   }
/*    */   
/*    */   public void renderButton(MatrixStack matrix, int x, int y, float z) {
/* 31 */     Minecraft minecraft = Minecraft.getInstance();
/*    */     
/* 33 */     boolean mouseHover = (x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height);
/* 34 */     minecraft.getTextureManager().bind(this.resourceLocation);
/*    */     
/* 36 */     if (mouseHover) {
/* 37 */       RenderSystem.color4f(0.8F, 0.8F, 0.8F, 1.0F);
/*    */     } else {
/* 39 */       RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     } 
/*    */     
/* 42 */     blit(matrix, this.x, this.y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
/*    */   }
/*    */ }
