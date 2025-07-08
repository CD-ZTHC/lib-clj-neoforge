(ns net.zthc.bases.consts
  (:require [clojure.string :as str]
            [net.zthc.bases.utils :as u])
  (:import
    [java.util.function Consumer]
    [net.neoforged.bus.api EventPriority IEventBus]
    [net.neoforged.fml ModContainer]
    (net.neoforged.neoforge.common NeoForge)))

(def ^:private event-bus! (atom nil))
(def ^:private mod-container! (atom nil))

(defn set-event-bus! [event-bus]
  (reset! event-bus! event-bus))

(defn set-mod-container! [mod-container]
  (reset! mod-container! mod-container))

(defn get-event-bus
  ^IEventBus []
  @event-bus!)

(defn get-mod-container
  ^ModContainer []
  @mod-container!)

(defn register-event
  [^Class event-type ^Consumer event]
  (IEventBus/.addListener NeoForge/EVENT_BUS event-type event))

(defn- fetch-event-name* [event]
  (some-> event
          class
          Class/.getName
          (str/split #"\.")
          last
          (str/replace #"$" "/")
          u/->kebab))

(def fetch-event-name (memoize fetch-event-name*))

(defmulti on-player fetch-event-name)

(defmethod on-player :default
  [event]
  (println "unhandled event:" (fetch-event-name event)))
