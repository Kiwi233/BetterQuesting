package betterquesting.items;

import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import betterquesting.core.BetterQuesting;
import betterquesting.lives.LifeManager;
import betterquesting.quests.QuestDatabase;

public class ItemExtraLife extends Item
{
	public ItemExtraLife()
	{
		this.setUnlocalizedName("betterquesting.extra_life");
		this.setCreativeTab(BetterQuesting.tabQuesting);
		this.setHasSubtypes(true);
	}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	if(stack.getItemDamage() != 0)
    	{
    		return stack;
    	} else if(QuestDatabase.bqHardcore)
    	{
    		if(!player.capabilities.isCreativeMode)
    		{
    			stack.stackSize--;
    		}
    		
    		if(LifeManager.getLives(player) >= LifeManager.maxLives)
    		{
	    		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("betterquesting.gui.full_lives")));
    			return stack;
    		}
    		
    		world.playSoundAtEntity(player, "random.levelup", 1F, 1F);
    		
    		if(!world.isRemote)
    		{
	    		LifeManager.AddRemoveLives(player, 1);
	    		player.addChatComponentMessage(new ChatComponentText(I18n.format("betterquesting.gui.remaining_lives", EnumChatFormatting.YELLOW + "" + LifeManager.getLives(player))));
    		}
    	} else if(!world.isRemote)
    	{
    		player.addChatComponentMessage(new ChatComponentText(I18n.format("betterquesting.msg.heart_disabled")));
    	}
    	
        return stack;
    }
    
    @Override
    public boolean hasEffect(ItemStack stack)
    {
    	return stack.getItemDamage() == 0;
    }
    
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        switch(stack.getItemDamage()%3)
        {
        	case 2:
        		return this.getUnlocalizedName() + ".quarter";
        	case 1:
        		return this.getUnlocalizedName() + ".half";
        	default:
        		return this.getUnlocalizedName() + ".full";	
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	list.add(new ItemStack(item, 1, 0));
    	list.add(new ItemStack(item, 1, 1));
    	list.add(new ItemStack(item, 1, 2));
    }
}