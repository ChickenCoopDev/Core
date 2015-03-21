package me.chickencoop.server;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin implements Listener {
	
	ArrayList<String> fly = new ArrayList<String>();
	ArrayList<String> pig = new ArrayList<String>();
	ArrayList<String> zombifypig = new ArrayList<String>();
	ArrayList<String> gadgetenabled = new ArrayList<String>();
	ArrayList<Player> vanished = new ArrayList<Player>();
	ArrayList<String> frozen = new ArrayList<String>(); 
	
	//ChickenCoop Commands
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("fly")) {
			if(!p.hasPermission("chickencoop.fly")) {
				p.sendMessage("§ePermissions: §7Only players with §eMod+ §7can do that!");
			} else { 
				if(!fly.contains(p.getName())) {
					fly.add(p.getName());
					p.setAllowFlight(true);
					p.setFlying(true);
					p.sendMessage("§eCondition: §7You are now able to fly!");
				} else {
					fly.remove(p.getName());
					p.setAllowFlight(false);
					p.setFlying(false);
					p.sendMessage("§eCondition: §7You are no longer able to fly!");
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("op")) {
			if(!p.hasPermission("chickencoop.op")) {
				p.sendMessage("§ePermissions: §7Only players with §eOwner+ §7can do that!");
			} else {
				if(args.length == 0) {
					p.sendMessage("§eUsage: §7/op <player>");
					return true;
				}
				Player optarget = Bukkit.getServer().getPlayer(args[0]);
				if(optarget == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				p.sendMessage("§e" + optarget.getName() + " §7has been opped!");
				optarget.setOp(true);
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("deop")) {
			if(!p.hasPermission("chickencoop.deop")) {
				p.sendMessage("§ePermissions: §7Only players with §eOwner+ §7can do that!");
			} else {
				if(args.length == 0) {
					p.sendMessage("§eUsage: §7/deop <player>");
					return true;
				}
				Player optarget = Bukkit.getServer().getPlayer(args[0]);
				if(optarget == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				p.sendMessage("§e" + optarget.getName() + " §7has been deopped!");
				optarget.setOp(false);
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("cc")) {
			if(!p.hasPermission("chickencoop.cc")) {
				p.sendMessage("§ePermissions: §7Only players with §eMod+ §7can do that!");
			} else {
				for (int i = 0; i < 100; i++) {
					Bukkit.broadcastMessage(" ");
				}
				Bukkit.broadcastMessage("§e§l" + p.getName() + " §7has cleared the chat!");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("gma")) {
			if(!p.hasPermission("chickencoop.gma")) {
				p.sendMessage("§ePermissions: §7Only players with §eAdmin+ §7can do that!");
			} else {
				p.setGameMode(GameMode.ADVENTURE);
				p.sendMessage("§eGamemode: §7Your gamemode has been set to adventure mode!");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("gmc")) {
			if(!p.hasPermission("chickencoop.gmc")) {
				p.sendMessage("§ePermissions: §7Only players with §eAdmin+ §7can do that!");
			} else {
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage("§eGamemode: §7Your gamemode has been set to creative mode!");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("gms")) {
			if(!p.hasPermission("chickencoop.gms")) {
				p.sendMessage("§ePermissions: §7Only players with §eAdmin+ §7can do that!");
			} else {
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage("§eGamemode: §7Your gamemode has been set to survival mode!");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("addgold")) {
			if(!p.hasPermission("chickencoop.addgold")) {
				p.sendMessage("§ePermissions: §7Only players with §eAdmin+ §7can do that!");
			} else {
				if(args.length == 0) {
					p.sendMessage("§eUsage: §7/addgold <player>");
					return true;
				}
				Player goldtarget = Bukkit.getServer().getPlayer(args[0]);
				if(goldtarget == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				giveGold(goldtarget, 150);
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("removegold")) {
			if(!p.hasPermission("chickencoop.removegold")) {
				p.sendMessage("§ePermissions: §7Only players with §eAdmin+ §7can do that!");
			} else {
				if(args.length == 0) {
					p.sendMessage("§eUsage: §7/removegold <player>");
					return true;
				}
				Player goldtarget = Bukkit.getServer().getPlayer(args[0]);
				if(goldtarget == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				takeGold(goldtarget, 150);
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("vanish")) {
			if(!p.hasPermission("chickencoop.vanish")) {
				p.sendMessage("§ePermissions: §7Only players with §eMod+ §7can do that!");
			} else {
				if(!vanished.contains(p)) {
					for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.hidePlayer(p);
					}
					vanished.add(p);
					p.sendMessage("§eCondition: §7You are now vanished!");
					return true;
				} else {
					for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
					}
					vanished.remove(p);
					p.sendMessage("§eCondition: §7You are no longer vanished!");
					return true;
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("re")) {
			if(!p.hasPermission("chickencoop.re")) {
				p.sendMessage("§ePermissions: §7Only players with §eOwner+ §7can do that!");
			} else {
				Bukkit.broadcastMessage("§a§lReload initialized...");
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 5, 5);
				Bukkit.getServer().reload();
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 5, 5);
				Bukkit.broadcastMessage("§a§lReload complete!");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("tphere")) {
			if(!p.hasPermission("chickencoop.tphere")) {
				p.sendMessage("§ePermissions: §7Only players with §eMod+ §7can do that!");
			} else {
				if(args.length == 0) {
					p.sendMessage("§eUsage: §7/tphere <player>");
					return true;
				}
				Player target3 = Bukkit.getServer().getPlayer(args[0]);
				if(target3 == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				target3.teleport(p);
				p.sendMessage("§7You have teleported §e" + target3.getName() + " §7to yourself.");
				target3.sendMessage("§e" + p.getName() + " §7has teleported you to them.");
				return true;
				//To-do /tphere
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("tp")) {
			if(!p.hasPermission("chickencoop.tp")) {
				p.sendMessage("§ePermissions: §7Only players with §eMod+ §7can do that!");
			} else {
				if(args.length == 0) {
					p.sendMessage("§eUsage: §7/tp <player>");
					return true;
				}
				Player target2 = Bukkit.getServer().getPlayer(args[0]);
				if(target2 == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				p.teleport(target2);
				p.sendMessage("§7You have teleported to §e" + target2.getName() + "§7.");
				target2.sendMessage("§e" + p.getName() + " §7has teleported to you.");
				return true;
				//To-do /tp
			}
		}
			
		
		if (cmd.getName().equalsIgnoreCase("freeze")) {
			if(!p.hasPermission("chickencoop.freeze")) {
				p.sendMessage("§ePermissions: §7Only players with §eDeputy+ §7can do that!");
			} else {
				if(args.length == 0) {
					sender.sendMessage("§eUsage: §7/freeze <player>");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null) {
					sender.sendMessage("§cHmm, I couldn't find " + args[0] + ", are they online?");
					return true;
				}
				if(frozen.contains(target.getName())) {
					frozen.remove(target.getName());
					sender.sendMessage("§e" + target.getName() + " §7is no longer frozen!");
					return true;
				}
				frozen.add(target.getName());
				sender.sendMessage("§e" + target.getName() + " §7is now frozen!");
				//To-do /freeze.
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(frozen.contains(p.getName())) {
			e.setTo(e.getFrom());
			//To-do /freeze
		}
	}
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onArrowLaunch(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Arrow)) {
			Arrow arrow = (Arrow) proj;
			LivingEntity shooter = (LivingEntity) arrow.getShooter();
			if((shooter instanceof Player)) {
				Player p = (Player) shooter;
				
				if(gadgetenabled.contains(p.getName())) {
					arrow.getLocation().getWorld().playEffect(arrow.getLocation(), Effect.SMOKE, 1000);
					arrow.getLocation().getWorld().playSound(p.getLocation(), Sound.FUSE, 5F, 5F);
					arrow.getLocation().getWorld().playEffect(arrow.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				}
			}
		}
	}
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Arrow)) {
			Arrow arrow = (Arrow) proj;
			LivingEntity shooter = (LivingEntity) arrow.getShooter();
			if ((shooter instanceof Player)) {
				Player p = (Player) shooter;
				
				if(gadgetenabled.contains(p.getName())) {
					arrow.getWorld().strikeLightning(arrow.getLocation());
				}
			}
		}
	}
	
	@EventHandler
	public void onEggLaunch(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Egg)) {
			Egg egg = (Egg) proj;
			LivingEntity shooter = (LivingEntity) egg.getShooter();
			if((shooter instanceof Player)) {
				Player p = (Player) shooter;
				
				if(gadgetenabled.contains(p.getName())) {
					egg.getLocation().getWorld().playEffect(egg.getLocation(), Effect.SMOKE, 1000);
					egg.getLocation().getWorld().playSound(p.getLocation(), Sound.FUSE, 5F, 5F);
					egg.getLocation().getWorld().playEffect(egg.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				}
			}
		}
	}
	
	@EventHandler
	public void onEggHit(ProjectileHitEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Egg)) {
			Egg egg = (Egg) proj;
			LivingEntity shooter = (LivingEntity) egg.getShooter();
			if ((shooter instanceof Player)) {
				Player p = (Player) shooter;
				if(pig.contains(p.getName())) {
					if(gadgetenabled.contains(p.getName())) {
						egg.getWorld().spawnEntity(egg.getLocation(), EntityType.PIG);
						Firework fw = (Firework) egg.getWorld().spawnEntity(egg.getLocation(),
								EntityType.FIREWORK);
						FireworkMeta fwmeta = fw.getFireworkMeta();
						FireworkEffect effect = FireworkEffect.builder().withTrail().withFlicker().withColor(Color.FUCHSIA)
								.with(FireworkEffect.Type.BALL).build();
						fwmeta.clearEffects();
						fwmeta.addEffect(effect);
						Field f;
						try {
							f = fwmeta.getClass().getDeclaredField("power");
							f.setAccessible(true);
							try {
								f.set(fwmeta, -1);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NoSuchFieldException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						fw.setFireworkMeta(fwmeta);
					}
				}
			}
		}
	}		
	
	@EventHandler
	public void onZombieEggLaunch(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Egg)) {
			Egg egg = (Egg) proj;
			LivingEntity shooter = (LivingEntity) egg.getShooter();
			if((shooter instanceof Player)) {
				Player p = (Player) shooter;
				
				if(gadgetenabled.contains(p.getName())) {
					egg.getLocation().getWorld().playEffect(egg.getLocation(), Effect.SMOKE, 1000);
					egg.getLocation().getWorld().playSound(p.getLocation(), Sound.FUSE, 5F, 5F);
					egg.getLocation().getWorld().playEffect(egg.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				}
			}
		}
	}
	
	@EventHandler
	public void onZombieEggHit(ProjectileHitEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Egg)) {
			Egg egg = (Egg) proj;
			LivingEntity shooter = (LivingEntity) egg.getShooter();
			if ((shooter instanceof Player)) {
				Player p = (Player) shooter;
				if(zombifypig.contains(p.getName())) {
					if(gadgetenabled.contains(p.getName())) {
						egg.getWorld().spawnEntity(egg.getLocation(), EntityType.PIG);
						
						Firework fw = (Firework) egg.getWorld().spawnEntity(egg.getLocation(),
								EntityType.FIREWORK);
						FireworkMeta fwmeta = fw.getFireworkMeta();
						FireworkEffect effect = FireworkEffect.builder().withTrail().withFlicker().withColor(Color.FUCHSIA)
								.with(FireworkEffect.Type.BALL).build();
						fwmeta.clearEffects();
						fwmeta.addEffect(effect);
						Field f;
						try {
							f = fwmeta.getClass().getDeclaredField("power");
							f.setAccessible(true);
							try {
								f.set(fwmeta, -1);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NoSuchFieldException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						fw.setFireworkMeta(fwmeta);
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							egg.getWorld().strikeLightning(egg.getLocation());
							Firework fw = (Firework) egg.getWorld().spawnEntity(egg.getLocation(),
									EntityType.FIREWORK);
							FireworkMeta fwmeta = fw.getFireworkMeta();
							FireworkEffect effect = FireworkEffect.builder().withTrail().withFlicker().withColor(Color.GREEN).withColor(Color.GRAY)
									.with(FireworkEffect.Type.BALL).build();
							fwmeta.clearEffects();
							fwmeta.addEffect(effect);
							Field f;
							try {
								f = fwmeta.getClass().getDeclaredField("power");
								f.setAccessible(true);
								try {
									f.set(fwmeta, -1);
								} catch (IllegalArgumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IllegalAccessException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} catch (NoSuchFieldException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SecurityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							fw.setFireworkMeta(fwmeta);
							egg.getWorld().spawnEntity(egg.getLocation(), EntityType.PIG_ZOMBIE);
						}
					}, 60);
				}
			}
		}
	}			
					
	@EventHandler
	public void onSnowballLaunch(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Snowball)) {
			Snowball snowball = (Snowball) proj;
			LivingEntity shooter = (LivingEntity) snowball.getShooter();
			if((shooter instanceof Player)) {
				Player p = (Player) shooter;
				
				if(gadgetenabled.contains(p.getName())) {
					snowball.getLocation().getWorld().playEffect(snowball.getLocation(), Effect.SMOKE, 1000);
					snowball.getLocation().getWorld().playSound(p.getLocation(), Sound.FUSE, 5F, 5F);
					snowball.getLocation().getWorld().playEffect(snowball.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				}
			}
		}
	}
	
	@EventHandler
	public void onSnowballHit(ProjectileHitEvent e) {
		Projectile proj = e.getEntity();
		if ((proj instanceof Snowball)) {
			Snowball snowball = (Snowball) proj;
			LivingEntity shooter = (LivingEntity) snowball.getShooter();
			if ((shooter instanceof Player)) {
				Player p = (Player) shooter;
				
				if(gadgetenabled.contains(p.getName())) {
					snowball.getWorld().spawnEntity(snowball.getLocation(), EntityType.PRIMED_TNT);
					Firework fw = (Firework) snowball.getWorld().spawnEntity(snowball.getLocation(),
							EntityType.FIREWORK);
					FireworkMeta fwmeta = fw.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder().withTrail().withFlicker().withFade(Color.RED).withColor(Color.WHITE).withColor(Color.RED)
							.with(FireworkEffect.Type.BALL).build();
					fwmeta.clearEffects();
					fwmeta.addEffect(effect);
					Field f;
					try {
						f = fwmeta.getClass().getDeclaredField("power");
						f.setAccessible(true);
						try {
							f.set(fwmeta, -1);
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NoSuchFieldException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					fw.setFireworkMeta(fwmeta);
				}
			}
		}
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		for (Block b : e.blockList()) {
			final BlockState state = b.getState();
			
			b.setType(Material.AIR); //Stops the item drops from happening.
			
			int delay = 0;
			
			if((b.getType() == Material.SAND || b.getType() == Material.GRAVEL)) {
				delay += 1;
			}
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					state.update(true, false);
				}
			}, delay);
		}
	}
	
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Action a = e.getAction();
		ItemStack is = e.getItem();
		
		if(a == Action.PHYSICAL || is == null || is.getType() == Material.AIR)
			return;
		
		if(is.getType() == Material.SLIME_BALL)
			openGadgets(e.getPlayer());
		if(is.getType() == Material.COMPASS)
			openServerSelector(e.getPlayer());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		for(Player p: vanished) {
			e.getPlayer().hidePlayer(p);
		}
		Player p = e.getPlayer();
		Bukkit.broadcastMessage("§eJoin§7> " + p.getName());
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 5F, 5F);
		ItemStack slimeball = new ItemStack(Material.SLIME_BALL);
		ItemMeta slimeballMeta = slimeball.getItemMeta();
		slimeballMeta.setDisplayName("§aGadget Menu");
		slimeball.setItemMeta(slimeballMeta);
		p.getInventory().setItem(4, slimeball);
		ItemStack serverselector = new ItemStack(Material.COMPASS);
		ItemMeta serverselectorMeta = serverselector.getItemMeta();
		serverselectorMeta.setDisplayName("§aServer Selector");
		serverselector.setItemMeta(serverselectorMeta);
		p.getInventory().setItem(0, serverselector);
		p.sendMessage("§7§m§l=============================================");
		p.sendMessage(" ");
		p.sendMessage("§eWelcome back to the ChickenCoop!");
		p.sendMessage("§aServer still in development.");
		p.sendMessage(" ");
		p.sendMessage("§7§m§l=============================================");
		if(!getConfig().contains(p.getName())) {
			getConfig().set(p.getName() + ".Gold", 0);
		}
		if(!getConfig().contains(p.getName())) {
			getConfig().set(p.getName() + ".joined", 1);
			setJoined(e.getPlayer(), 1);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		vanished.remove(e.getPlayer());
		Bukkit.broadcastMessage("§eQuit§7> " + p.getName());
		
	}
	
	private void openServerSelector(Player p) {
		Inventory serverselector = Bukkit.createInventory(null, 27, "§aServer Selector");
		
		ItemStack pvp = new ItemStack(Material.GOLD_SWORD);
		ItemMeta pvpMeta = pvp.getItemMeta();
		pvpMeta.setDisplayName("§aPvP Server");
		pvp.setItemMeta(pvpMeta);
		
		serverselector.setItem(10, pvp);
		p.openInventory(serverselector);
	}
	
	private void openGadgets(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "§aGadget Menu");
		
		ItemStack lightninglauncher = new ItemStack(Material.BOW);
		ItemMeta lightninglauncherMeta = lightninglauncher.getItemMeta(); 
		lightninglauncherMeta.setDisplayName("§eLightning Bow");
		lightninglauncherMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		lightninglauncher.setItemMeta(lightninglauncherMeta);
		
		ItemStack snowballgrenade = new ItemStack(Material.SNOW_BALL);
		ItemMeta snowballgrenadeMeta = snowballgrenade.getItemMeta();
		snowballgrenadeMeta.setDisplayName("§eSnowball Grenade");
		snowballgrenade.setItemMeta(snowballgrenadeMeta);
		
		ItemStack pokeball = new ItemStack(Material.EGG);
		ItemMeta pokeballMeta = pokeball.getItemMeta();
		pokeballMeta.setDisplayName("§ePig Pokeball");
		pokeball.setItemMeta(pokeballMeta);
		
		ItemStack zombiepig = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
		ItemMeta zombiepigMeta = zombiepig.getItemMeta();
		zombiepigMeta.setDisplayName("§eZombified Pig Pokeball");
		zombiepig.setItemMeta(zombiepigMeta);
		
		inv.setItem(11, lightninglauncher);
		inv.setItem(13, pokeball);
		inv.setItem(15, snowballgrenade);
		inv.setItem(22, zombiepig);
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gadget Menu"))
			return;
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		
		if(!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Server Selector"))
			e.setCancelled(true);
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
			p.closeInventory();
			return;
		}
		
		switch(e.getCurrentItem().getType()) {
		case PORK:
			if(zombifypig.contains(p.getName())) {
				zombifypig.remove(p.getName());
			}
			if(gadgetenabled.contains(p.getName())) {
				gadgetenabled.remove(p.getName());
				ItemStack zombiepig = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
				ItemMeta zombiepigMeta = zombiepig.getItemMeta();
				zombiepigMeta.setDisplayName("§eZombified Pig Pokeball");
				zombiepig.setItemMeta(zombiepigMeta);
				p.getInventory().remove(zombiepig);
				p.sendMessage("§eGadgets: §7You have disabled the §eZombified Pig Pokeball §7gadget!");
				p.closeInventory();
			} else {
				gadgetenabled.add(p.getName());
				zombifypig.add(p.getName());
				ItemStack zombiepig = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
				ItemMeta zombiepigMeta = zombiepig.getItemMeta();
				zombiepigMeta.setDisplayName("§eZombified Pig Pokeball");
				zombiepig.setItemMeta(zombiepigMeta);
				p.getInventory().setItem(1, zombiepig);
				p.sendMessage("§eGadgets: §7You have enabled the §eZombified Pig Pokeball §7gadget!");
				p.closeInventory();
			}
			break;
		case EGG:
			if(pig.contains(p.getName())) {
				pig.remove(p.getName());
			}
			if(gadgetenabled.contains(p.getName())) {
				gadgetenabled.remove(p.getName());
				ItemStack pokeball = new ItemStack(Material.EGG);
				ItemMeta pokeballMeta = pokeball.getItemMeta();
				pokeballMeta.setDisplayName("§ePig Pokeball");
				pokeball.setItemMeta(pokeballMeta);
				p.getInventory().remove(pokeball);
				p.sendMessage("§eGadgets: §7You have disabled the §ePig Pokeball §7gadget!");
				p.closeInventory();
			} else {
				gadgetenabled.add(p.getName());
				pig.add(p.getName());
				ItemStack pokeball = new ItemStack(Material.EGG);
				ItemMeta pokeballMeta = pokeball.getItemMeta();
				pokeballMeta.setDisplayName("§ePig Pokeball");
				pokeball.setItemMeta(pokeballMeta);
				p.getInventory().setItem(1, pokeball);
				p.sendMessage("§eGadgets: §7You have enabled the §ePig Pokeball §7gadget!");
				p.closeInventory();
			}
			break;
		case BOW:
			if(gadgetenabled.contains(p.getName())) {
				gadgetenabled.remove(p.getName());
				ItemStack lightninglauncher = new ItemStack(Material.BOW);
				ItemMeta lightninglauncherMeta = lightninglauncher.getItemMeta(); 
				lightninglauncherMeta.setDisplayName("§eLightning Bow");
				lightninglauncherMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				lightninglauncher.setItemMeta(lightninglauncherMeta);
				p.getInventory().remove(lightninglauncher);
				p.getInventory().remove(new ItemStack(Material.ARROW));
				p.sendMessage("§eGadgets: §7You have disabled the §eLightning Bow §7gadget!");
				p.closeInventory();
			} else {
				gadgetenabled.add(p.getName());
				p.getInventory().setItem(1, new ItemStack(Material.AIR));
				ItemStack lightninglauncher = new ItemStack(Material.BOW);
				ItemMeta lightninglauncherMeta = lightninglauncher.getItemMeta(); 
				lightninglauncherMeta.setDisplayName("§eLightning Bow");
				lightninglauncherMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				lightninglauncher.setItemMeta(lightninglauncherMeta);
				p.getInventory().setItem(1, lightninglauncher);
				p.getInventory().setItem(27, new ItemStack(Material.ARROW, 1));
				p.closeInventory();
				p.sendMessage("§eGadgets: §7You have enabled the §eLightning Bow §7gadget!");
			}
			break;
		case SNOW_BALL:
			if(gadgetenabled.contains(p.getName())) {
				gadgetenabled.remove(p.getName());
				ItemStack snowballgrenade = new ItemStack(Material.SNOW_BALL);
				ItemMeta snowballgrenadeMeta = snowballgrenade.getItemMeta();
				snowballgrenadeMeta.setDisplayName("§eSnowball Grenade");
				snowballgrenade.setItemMeta(snowballgrenadeMeta);
				p.getInventory().remove(snowballgrenade);
				p.sendMessage("§eGadgets: §7You have disabled the §eSnowball Grenade §7gadget!");
				p.closeInventory();
			} else {
				gadgetenabled.add(p.getName());
				p.getInventory().setItem(1, new ItemStack(Material.AIR));
				ItemStack snowballgrenade = new ItemStack(Material.SNOW_BALL);
				ItemMeta snowballgrenadeMeta = snowballgrenade.getItemMeta();
				snowballgrenadeMeta.setDisplayName("§eSnowball Grenade");
				snowballgrenade.setItemMeta(snowballgrenadeMeta);
				p.getInventory().setItem(1, snowballgrenade);
				p.sendMessage("§eGadgets: §7You have enabled the §eSnowball Grenade §7gadget!");
				p.closeInventory();
			}
		default:
			p.closeInventory();
			break;
		}
	}
	
	public void giveGold(Player p, int i) {
		getConfig().set(p.getName() + ".Gold", getConfig().getInt(p.getName() + ".Gold", 0) + i);
		saveConfig();
		p.sendMessage("§eMoney: §7Your gold has increased by " + i + ".");
	}
	
	public void takeGold(Player p, int i) {
		getConfig().set(p.getName() + ".Gold", getConfig().getInt(p.getName() + ".Gold", 0) - i);
		saveConfig();
		p.sendMessage("§eMoney: §7Your gold has decreased by " + i + ".");
	}
	
	public void setJoined(Player p, int i) {
		getConfig().set(p.getName() + ".joined", getConfig().getInt(p.getName() + ".joined", 0) + i);
		saveConfig();
	}

}