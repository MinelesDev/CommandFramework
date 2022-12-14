/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Kafein's CommandFramework
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.kafeintr.commands.jda.component;

import com.github.kafeintr.commands.common.component.SenderComponent;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;

public final class JDASenderComponent implements SenderComponent {

    @NotNull
    private final Member member;

    @Nullable
    private final SlashCommandInteractionEvent event;

    @Nullable
    private final InteractionHook reply;

    public JDASenderComponent(@NotNull Member member, @Nullable SlashCommandInteractionEvent event,
                              @Nullable InteractionHook reply) {
        this.member = member;
        this.event = event;
        this.reply = reply;
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
        sendMessage(message, true);
    }

    public void sendMessage(@NotNull String message, boolean ephemeral) {
        if (reply == null) {
            event.reply(message).setEphemeral(ephemeral).queue();
        } else {
            reply.setEphemeral(ephemeral).editOriginal(message).queue();
        }
    }

    public void sendMessage(@NotNull Message message, boolean ephemeral) {
        if (reply == null) {
            event.reply(message).setEphemeral(ephemeral).queue();
        } else {
            reply.setEphemeral(ephemeral).editOriginal(message).queue();
        }
    }

    @Nullable
    public SlashCommandInteractionEvent getEvent() {
        return event;
    }

    @Nullable
    public InteractionHook getReply() {
        return this.reply;
    }

    @NotNull
    public Member getMember() {
        return this.member;
    }

    @NotNull
    public TextChannel getChannel() {
        return event.getTextChannel();
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
