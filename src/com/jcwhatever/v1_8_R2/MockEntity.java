package com.jcwhatever.v1_8_R2;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A mock implementation of an {@link Entity}.
 */
public class MockEntity implements Entity {

    private final UUID _uuid;
    private final int _id;
    private final EntityType _type;
    private Location _location = new Location(new MockWorld("dummy"), 0, 0, 0);
    private Vector _velocity = new Vector(0, 0, 0);
    private Entity _passenger;
    private String _customName;
    private boolean _isCustomNameVisible;
    private boolean isDead = false;

    /**
     * Constructor.
     *
     * @param type  The entity type.
     */
    public MockEntity(int id, EntityType type) {
        _id = id;
        _type = type;
        _uuid = UUID.randomUUID();
    }

    @Override
    public Location getLocation() {
        return _location;
    }

    @Override
    public Location getLocation(Location location) {
        return location;
    }

    @Override
    public void setVelocity(Vector vector) {
        _velocity = vector;
    }

    @Override
    public Vector getVelocity() {
        return _velocity;
    }

    @Override
    public boolean isOnGround() {
        return false;
    }

    @Override
    public World getWorld() {
        return _location.getWorld();
    }

    @Override
    public boolean teleport(Location location) {
        return teleport(location, TeleportCause.PLUGIN);
    }

    @Override
    public boolean teleport(Location location, TeleportCause teleportCause) {
        _location = new Location(location.getWorld(),
                location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());
        return true;
    }

    @Override
    public boolean teleport(Entity entity) {
        return teleport(entity, TeleportCause.PLUGIN);
    }

    @Override
    public boolean teleport(Entity entity, TeleportCause teleportCause) {
        return teleport(entity.getLocation(), teleportCause);
    }

    @Override
    public List<Entity> getNearbyEntities(double v, double v1, double v2) {
        return new ArrayList<>(0);
    }

    @Override
    public int getEntityId() {
        return _id;
    }

    @Override
    public int getFireTicks() {
        return 0;
    }

    @Override
    public int getMaxFireTicks() {
        return 0;
    }

    @Override
    public void setFireTicks(int i) {

    }

    @Override
    public void remove() {
        isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void sendMessage(String s) {

    }

    @Override
    public void sendMessage(String[] strings) {

    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Entity getPassenger() {
        return _passenger;
    }

    @Override
    public boolean setPassenger(Entity entity) {
        _passenger = entity;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return _passenger == null;
    }

    @Override
    public boolean eject() {

        if (_passenger == null)
            return false;

        _passenger = null;

        return true;
    }

    @Override
    public float getFallDistance() {
        return 0;
    }

    @Override
    public void setFallDistance(float v) {

    }

    @Override
    public void setLastDamageCause(EntityDamageEvent entityDamageEvent) {

    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return null;
    }

    @Override
    public UUID getUniqueId() {
        return _uuid;
    }

    @Override
    public int getTicksLived() {
        return 0;
    }

    @Override
    public void setTicksLived(int i) {

    }

    @Override
    public void playEffect(EntityEffect entityEffect) {

    }

    @Override
    public EntityType getType() {
        return _type;
    }

    @Override
    public boolean isInsideVehicle() {
        return false;
    }

    @Override
    public boolean leaveVehicle() {
        return false;
    }

    @Override
    public Entity getVehicle() {
        return null;
    }

    @Override
    public void setCustomName(String s) {
        _customName = s;
    }

    @Override
    public String getCustomName() {
        return _customName;
    }

    @Override
    public void setCustomNameVisible(boolean b) {
        _isCustomNameVisible = b;
    }

    @Override
    public boolean isCustomNameVisible() {
        return _isCustomNameVisible;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {

    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return new ArrayList<>(0);
    }

    @Override
    public boolean hasMetadata(String s) {
        return false;
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {

    }

    @Override
    public boolean isPermissionSet(String s) {
        return false;
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return false;
    }

    @Override
    public boolean hasPermission(String s) {
        return false;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return false;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return null;
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public void setOp(boolean b) {

    }
}
