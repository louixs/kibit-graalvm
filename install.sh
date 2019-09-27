#!/bin/bash

INSTALL_PATH=/usr/local/bin
COMMANDS_PATH=$(pwd)
COMMAND=kibit-graalvm

if [ ! -z "$1" ]; then
  INSTALL_PATH="$1"
fi

function exit_on_error() {
  if [ "$?" -ne 0 ]; then
    echo "There were some issues. Please check."
    exit 1
  fi
}

function install() {
  local command=$1
  local install_path=$2

  echo "Installing $command ..."
  echo "Making $command executable"
  chmod +x "$command"

  if [ -L "$install_path/$command" ]; then
    echo "Removing existing $command from $install_path first ..."
    rm "$install_path/$command"
  fi

  echo "Symlinking $command to $install_path ..."
  ln -s "$COMMANDS_PATH/$command" "$install_path/$command"

  exit_on_error
  echo "Done"
}

install "$COMMAND" "$INSTALL_PATH"
