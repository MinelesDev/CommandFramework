package com.mineles.commands.jda.component;

import com.mineles.commands.common.component.SenderComponent;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;

public final class JDASenderComponent implements SenderComponent {

    private final Member member;
    private final MessageChannel channel;

    public JDASenderComponent(@NotNull Member member, @NotNull MessageChannel messageChannel) {
        this.member = member;
        this.channel = messageChannel;
    }

    @Override
    public @Nullable UUID getUniqueId() {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return this.member.getUser().getName();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        Message jdaMessage = this.channel.sendMessage(message).complete();

        jdaMessage.delete().queueAfter(5, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        Permission jdaPermission = Permission.valueOf(permission.toUpperCase(Locale.ROOT));

        return this.member.hasPermission(jdaPermission);
    }

    @Override
    public boolean isOnline() {
        return this.member.getOnlineStatus() == OnlineStatus.ONLINE;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }
}
