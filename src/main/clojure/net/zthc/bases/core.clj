(ns net.zthc.bases.core
  (:require [net.zthc.bases.consts :as consts]
            [net.zthc.bases.events.player :as e.player]))

(definterface IMod
  (init [^net.neoforged.bus.api.IEventBus event-bus
         ^net.neoforged.fml.ModContainer mod-container]))

(deftype ZTHCMod []
  :load-ns true
  IMod
  (init [_ event-bus mod-container]
    (println "hello from libclj!")
    (consts/set-event-bus! event-bus)
    (consts/set-mod-container! mod-container)
    (e.player/register)
    ))
