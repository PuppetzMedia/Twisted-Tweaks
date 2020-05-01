package uk.artdude.tweaks.twisted.common.achievement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomTrigger implements ICriterionTrigger<CustomTrigger.Instance>
{
	private ResourceLocation ID;
	private final Map<PlayerAdvancements, CustomTrigger.Listeners> listeners = Maps.<PlayerAdvancements, CustomTrigger.Listeners>newHashMap();

	public CustomTrigger(String path)
	{
		ID = new ResourceLocation(References.modID, path);
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<CustomTrigger.Instance> listener) {
		CustomTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

		if (consumeitemtrigger$listeners == null) {
			consumeitemtrigger$listeners = new CustomTrigger.Listeners(playerAdvancementsIn);
			this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
		}

		consumeitemtrigger$listeners.add(listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<CustomTrigger.Instance> listener) {
		CustomTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

		if (consumeitemtrigger$listeners != null) {
			consumeitemtrigger$listeners.remove(listener);

			if (consumeitemtrigger$listeners.isEmpty()) {
				this.listeners.remove(playerAdvancementsIn);
			}
		}
	}

	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
		this.listeners.remove(playerAdvancementsIn);
	}

	@Override
	public CustomTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		return new CustomTrigger.Instance(this.ID);
	}

	public static class Instance extends CriterionInstance {

		public Instance(ResourceLocation id) {
			super(id);
		}
	}

	public void trigger(PlayerEntity player) {
		CustomTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancement());

		if (enterblocktrigger$listeners != null) {
			enterblocktrigger$listeners.trigger();
		}
	}

	static class Listeners {
		private final PlayerAdvancements playerAdvancements;
		private final Set<Listener<Instance>> listeners = Sets.<Listener<CustomTrigger.Instance>>newHashSet();

		public Listeners(PlayerAdvancements playerAdvancementsIn) {
			this.playerAdvancements = playerAdvancementsIn;
		}

		public boolean isEmpty() {
			return this.listeners.isEmpty();
		}

		public void add(Listener<CustomTrigger.Instance> listener) {
			this.listeners.add(listener);
		}

		public void remove(Listener<CustomTrigger.Instance> listener) {
			this.listeners.remove(listener);
		}

		public void trigger() {
			List<Listener<Instance>> list = null;

			for (Listener<CustomTrigger.Instance> listener : this.listeners) {
				if (list == null) {
					list = Lists.newArrayList();
				}

				list.add(listener);
			}

			if (list != null) {
				for (Listener<CustomTrigger.Instance> listener1 : list) {
					listener1.grantCriterion(this.playerAdvancements);
				}
			}
		}
	}
}