(ns net.zthc.bases.events.player
  (:require [net.zthc.bases.consts :as consts])
  (:import
    ;; 核心玩家事件
   (net.minecraft.world.entity.player Player)
   (net.neoforged.neoforge.event.entity.player
    AttackEntityEvent
    PlayerEvent$PlayerLoggedInEvent
    PlayerEvent$PlayerLoggedOutEvent
    PlayerEvent$PlayerRespawnEvent
    PlayerEvent$PlayerChangedDimensionEvent
    PlayerEvent$ItemCraftedEvent
    PlayerInteractEvent$RightClickBlock
    PlayerInteractEvent$LeftClickBlock
    PlayerWakeUpEvent)
   (net.neoforged.neoforge.event.entity.living LivingDeathEvent)))

(defn register
  "注册所有与玩家相关的核心事件。"
  []
  ;; 玩家攻击事件
  (consts/register-event AttackEntityEvent consts/on-player)

  ;; 玩家连接事件
  (consts/register-event PlayerEvent$PlayerLoggedInEvent consts/on-player)
  (consts/register-event PlayerEvent$PlayerLoggedOutEvent consts/on-player)

  ;; 玩家状态变更事件
  (consts/register-event PlayerEvent$PlayerRespawnEvent consts/on-player)
  (consts/register-event PlayerEvent$PlayerChangedDimensionEvent consts/on-player)
  (consts/register-event LivingDeathEvent (fn [event]
                                            (when (instance? Player (LivingDeathEvent/.getEntity event))
                                              (consts/on-player event))))

  ;; 玩家交互事件
  (consts/register-event PlayerInteractEvent$RightClickBlock consts/on-player)
  (consts/register-event PlayerInteractEvent$LeftClickBlock consts/on-player)

  ;; 物品合成事件
  (consts/register-event PlayerEvent$ItemCraftedEvent consts/on-player)

  ;; 世界交互事件
  (consts/register-event PlayerWakeUpEvent consts/on-player))