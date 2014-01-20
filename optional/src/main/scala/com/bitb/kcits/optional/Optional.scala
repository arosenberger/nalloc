package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object Optional {
  final private[this] val None = new Optional[Null](null)

  def empty[A >: Null] = None
  def apply[A >: Null](value: A): Optional[A] = if (value == null) None else new Optional[A](value)
  def unapply[A >: Null](value: A): Optional[A] = if (value == null) None else Optional[A](value)
}

final class Optional[+A >: Null](val value: A) extends AnyVal {
  def get: A = value
  def isEmpty = value == null

  def map[B](f: A => B): B = macro OptionalMacros.map_impl[A, B]
}