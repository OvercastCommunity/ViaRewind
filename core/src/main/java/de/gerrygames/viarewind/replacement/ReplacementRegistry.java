package de.gerrygames.viarewind.replacement;

import de.gerrygames.viarewind.storage.BlockState;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectMap;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

public class ReplacementRegistry {
	private final Int2ObjectMap<Replacement> itemReplacements = new Int2ObjectOpenHashMap<>();
	private final Int2ObjectMap<Replacement> blockReplacements = new Int2ObjectOpenHashMap<>();


	public void registerItem(int id, Replacement replacement) {
		registerItem(id, -1, replacement);
	}

	public void registerBlock(int id, Replacement replacement) {
		registerBlock(id, -1, replacement);
	}

	public void registerItemBlock(int id, Replacement replacement) {
		registerItemBlock(id, -1, replacement);
	}

	public void registerItem(int id, int data, Replacement replacement) {
		itemReplacements.put(combine(id, data), replacement);
	}

	public void registerBlock(int id, int data, Replacement replacement) {
		blockReplacements.put(combine(id, data), replacement);
	}

	public void registerItemBlock(int id, int data, Replacement replacement) {
		registerItem(id, data, replacement);
		registerBlock(id, data, replacement);
	}

	public Item replace(Item item) {
		Replacement replacement = itemReplacements.get(combine(item.getIdentifier(), item.getData()));
		if (replacement==null) replacement = itemReplacements.get(combine(item.getIdentifier(), -1));
		return replacement==null ? item : replacement.replace(item);
	}

	public BlockState replace(BlockState block) {
		Replacement replacement = blockReplacements.get(combine(block.getId(), block.getData()));
		if (replacement==null) replacement = blockReplacements.get(combine(block.getId(), -1));
		return replacement==null ? block : replacement.replace(block);
	}

	public static int combine(int id, int data) {
		return (id << 16) | (data & 0xFFFF);
	}
}
