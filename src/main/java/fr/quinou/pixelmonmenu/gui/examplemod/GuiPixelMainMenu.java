 package fr.quinou.pixelmonmenu.gui.examplemod;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.mojang.blaze3d.matrix.MatrixStack;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.awt.Desktop;
/*     */ import java.net.URI;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import fr.quinou.pixelmonmenu.gui.pixelmenu.PixelButton;
 import net.minecraft.client.gui.screen.ConnectingScreen;
/*     */ import net.minecraft.client.gui.screen.OptionsScreen;
/*     */ import net.minecraft.client.gui.screen.Screen;
/*     */ import net.minecraft.client.gui.screen.WorldSelectionScreen;
/*     */ import net.minecraft.client.gui.widget.Widget;
/*     */ import net.minecraft.client.gui.widget.button.Button;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.network.ServerPinger;
/*     */ import net.minecraft.util.DefaultUncaughtExceptionHandler;
 import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.StringTextComponent;
/*     */ import net.minecraft.util.text.TranslationTextComponent;
/*     */ import net.minecraftforge.api.distmarker.Dist;
 import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ public class GuiPixelMainMenu extends Screen {
/*  33 */   private static final Logger LOGGER = LogManager.getLogger();
/*  34 */   private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("pixelmenu", "textures/gui/fond.png");
/*     */   
/*     */   private final boolean fading;
/*     */   
/*     */   private long fadeInStart;
/*  39 */   private static final ResourceLocation REJOINDRE_LE_SERVEUR = new ResourceLocation("pixelmenu", "textures/gui/rejoindre-le-serveur.png");
/*  40 */
/*  39 */   private static final ResourceLocation DISCORDBUTTON = new ResourceLocation("pixelmenu", "textures/gui/1discord.png");
 /*  39 */   private static final ResourceLocation BOUTIQUE = new ResourceLocation("pixelmenu", "textures/gui/boutique.png");


 private static final ResourceLocation SOLO = new ResourceLocation("pixelmenu", "textures/gui/solo.png");
/*  41 */   private static final ResourceLocation OPTIONS = new ResourceLocation("pixelmenu", "textures/gui/options.png");
/*  42 */   //private static final ResourceLocation QUITTER_LE_JEU = new ResourceLocation("pixelmenu", "textures/gui/quitter-le-jeu.png");
/*  43 */   private static final ResourceLocation DISCORD = new ResourceLocation("pixelmenu", "textures/gui/discord.png");
/*  44 */   private static final ResourceLocation TWITTER = new ResourceLocation("pixelmenu", "textures/gui/site.png");
/*  45 */   private static final ResourceLocation TIKTOK = new ResourceLocation("pixelmenu", "textures/gui/tiktok.png");
/*  46 */   private static final ResourceLocation POKEWIKI = new ResourceLocation("pixelmenu", "textures/gui/x.png");
/*  48 */   private static final ResourceLocation ONLINE = new ResourceLocation("pixelmenu", "textures/gui/online.png");
/*  49 */   private static final ResourceLocation OFFLINE = new ResourceLocation("pixelmenu", "textures/gui/offline.png");
/*     */   
/*  51 */   private final ServerPinger serverPinger = new ServerPinger();
/*  52 */   private ServerData server = new ServerData("server", "play.pixelmonsh.fr", false);
/*  53 */   private static final ScheduledThreadPoolExecutor THREAD_POOL = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER)).build());
/*  54 */   private static long ping = -1L;
/*     */   
/*     */   public GuiPixelMainMenu() {
/*  57 */     this(false);
/*  58 */     THREAD_POOL.scheduleAtFixedRate(this::pinger, 0L, 5L, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */   private void pinger() {
/*  62 */     ping = this.server.ping;
/*     */     
/*  64 */     this.server.pinged = true;
/*  65 */     this.server.motd = StringTextComponent.EMPTY;
/*  66 */     this.server.playerList = new ArrayList();
/*     */     
/*     */     try {
/*  69 */       this.serverPinger.pingServer(this.server, () -> {
/*     */
/*     */           });
/*  72 */     } catch (UnknownHostException unknownhostexception) {
/*  73 */       this.server.ping = -2L;
/*  74 */     } catch (Exception exception) {
/*  75 */       this.server.ping = -1L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public GuiPixelMainMenu(boolean fading) {
/*  80 */     super((ITextComponent)new TranslationTextComponent("narrator.screen.title"));
/*  81 */     this.fading = fading;
/*     */   }
/*     */   
/*     */   public void tick() {
/*  85 */     super.tick();
/*  86 */     this.serverPinger.tick();
/*     */   }
/*     */   
/*     */   public boolean isPauseScreen() {
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   public void render(MatrixStack matrix, int a, int b, float c) {
/*  98 */     if (this.fadeInStart == 0L && this.fading) {
/*  99 */       this.fadeInStart = Util.getMillis();
/*     */     }
/*     */     
/* 102 */     float f = this.fading ? ((float)(Util.getMillis() - this.fadeInStart) / 1000.0F) : 1.0F;
/* 103 */     fill(matrix, 0, 0, this.width, this.height, -16777216);
/*     */     
/* 105 */     this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);
/* 106 */     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 107 */     blit(matrix, 0, 0, 0.0F, 0.0F, 1, 1, this.width, this.height);
/*     */     
/* 109 */     int j = this.width / 2 - 137;
/* 110 */     RenderSystem.enableBlend();
/* 111 */     RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 112 */     RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.fading ? MathHelper.floor(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
/* 113 */     blit(matrix, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
/*     */     
/* 115 */     int rejoindrex = this.width / 2 - this.width / 3 / 2;
/* 116 */     int buttonxY = (int)(this.height / 2.45D);
/* 117 */     addButton((Widget)new PixelButton(rejoindrex, buttonxY, this.width / 3, this.height / 14, REJOINDRE_LE_SERVEUR, p_214132_1_ -> join(this.server)));
/*     */ 
/*     */ 
/*     */     
/* 121 */     buttonxY += this.height / 14 + this.height / 14 / 4;
/* 122 */     addButton((Widget)new PixelButton(rejoindrex, buttonxY, (int)(this.width / 6.5D), this.height / 14, SOLO, p_214132_1_ -> this.minecraft.setScreen((Screen)new WorldSelectionScreen(this))));
/*     */ 

 /*     */
/* 126 */     int rightbtnx = rejoindrex + this.width / 3 - (int)(this.width / 6.5D);
/*     */     
/* 128 */     addButton((Widget)new PixelButton(rightbtnx, buttonxY, (int)(this.width / 6.5D), this.height / 14, OPTIONS, p_214132_1_ -> this.minecraft.setScreen((Screen)new OptionsScreen(this, this.minecraft.options))));
/*     */
              buttonxY += this.height / 14 + this.height / 14 / 4;
              addButton((Widget)new PixelButton(rejoindrex, buttonxY, (int)(this.width / 6.5D), this.height / 14, DISCORDBUTTON, p_214132_1_ -> openURL("https://discord.gg/b4RRqDwbKt")));
    /* 128 */ addButton((Widget)new PixelButton(rightbtnx, buttonxY, (int)(this.width / 6.5D), this.height / 14, BOUTIQUE, p_214132_1_ -> openURL("https://pixelmonsh.fr/shop/categories/grades")));

    /*     */
/* 132 */     //buttonxY += this.height / 14 + this.height / 14 / 4;
/* 133 */     //addButton((Widget)new PixelButton(rejoindrex, buttonxY, this.width / 3, this.height / 14, QUITTER_LE_JEU, p_214132_1_ -> this.minecraft.stop()));
/*     */ 
/*     */ 
/*     */     
/* 137 */     int leftx = (int)(this.width * 0.045D) - this.height / 9 / 2;
/* 138 */     int lefty = this.height / 3;
/* 139 */     addButton((Widget)new PixelButton(leftx, lefty, this.height / 16, this.height / 17, DISCORD, p_214132_1_ -> openURL("https://discord.gg/b4RRqDwbKt")));
/*     */ 
/*     */ 
/*     */     
/* 143 */     lefty += this.height / 9 + this.height / 9 / 3;
/*     */     
/* 145 */     addButton((Widget)new PixelButton(leftx, lefty, this.height / 16, this.height / 16, TWITTER, p_214132_1_ -> openURL("https://pixelmonsh.fr/")));
/*     */ 
/*     */ 
/*     */     
/* 149 */     lefty += this.height / 9 + this.height / 9 / 3;
/*     */     
/* 151 */     addButton((Widget)new PixelButton(leftx, lefty, this.height / 16, this.height / 16, TIKTOK, p_214132_1_ -> openURL("https://www.tiktok.com/@play.pixelmonsh.fr")));
/*     */ 
/*     */ 
/*     */     
/* 155 */     int wikix = (int)(this.width * 0.037D) - this.height / 12 / 2;
/* 156 */     int wikiy = (int)(this.width * 0.045D) - this.height / 12 / 2;
/*     */     
/* 158 */     addButton((Widget)new PixelButton(wikix, wikiy, this.height / 18, this.height / 18, POKEWIKI, p_214132_1_ -> this.minecraft.stop()));/*     */
/*     */
/*     */
/*     */ 
/*     */     
/* 169 */     if (ping > 0L) {
/* 170 */       this.minecraft.getTextureManager().bind(ONLINE);
/*     */     } else {
/* 172 */       this.minecraft.getTextureManager().bind(OFFLINE);
/*     */     } 
/* 174 */     blit(matrix, this.width - 10, 2, 0.0F, 0.0F, 8, 8, 8, 8);
/*     */     
/* 176 */     super.render(matrix, a, b, c);
/*     */   }
/*     */   
/*     */   private void openURL(String yourURL) {
/*     */     try {
/* 181 */       if (Desktop.isDesktopSupported()) {
/*     */         
/* 183 */         Desktop.getDesktop().browse(new URI(yourURL));
/*     */       }
/*     */       else {
/*     */         
/* 187 */         Runtime runtime = Runtime.getRuntime();
/* 188 */         if (System.getenv("OS") != null && System.getenv("OS").contains("Windows"))
/* 189 */         { runtime.exec("rundll32 url.dll,FileProtocolHandler " + yourURL); }
/*     */         else
/* 191 */         { runtime.exec("xdg-open " + yourURL); } 
/*     */       } 
/* 193 */     } catch (Exception e) {
/* 194 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void join(ServerData serverdat) {
/* 199 */     this.minecraft.setScreen((Screen)new ConnectingScreen(this, this.minecraft, serverdat));
/*     */   }
/*     */   
/*     */   public boolean mouseClicked(double xmouse, double ymouse, int zmouse) {
/* 203 */     return super.mouseClicked(xmouse ,ymouse, zmouse);
/*     */   }
/*     */   
/*     */   public void func_231164_f_() {
/* 207 */     THREAD_POOL.remove(this::pinger);
/*     */   }
/*     */ }
