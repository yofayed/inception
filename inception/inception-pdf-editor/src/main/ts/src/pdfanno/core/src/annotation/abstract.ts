import EventEmitter from 'events'
import appendChild from '../render/appendChild'
import { DEFAULT_RADIUS } from '../render/renderKnob'
import { dispatchWindowEvent } from '../../../shared/util'

/**
 * Abstract Annotation Class.
 */
export default abstract class AbstractAnnotation extends EventEmitter {
  uuid: string = null
  color: string = null
  deleted = false
  disabled = false
  selected = false
  readOnly = false
  hoverEventDisable = false
  selectedTime = null
  element: HTMLElement = null
  exportId: any
  type: 'span' | 'relation'

  /**
   * Constructor.
   */
  constructor () {
    super()
    this.autoBind()
  }

  /**
   * Bind the `this` scope of instance methods to `this`.
   */
  autoBind () {
    Object.getOwnPropertyNames(this.constructor.prototype)
      .filter(prop => typeof this[prop] === 'function')
      .forEach(method => {
        this[method] = this[method].bind(this)
      })
  }

  abstract setHoverEvent(): void;

  /**
   * Render annotation(s).
   */
  render (): boolean {
    this.element.remove()

    if (this.deleted) {
      return false
    }

    const base = document.getElementById('annoLayer2')
    this.element = appendChild(base, this)

    if (!this.hoverEventDisable && this.setHoverEvent) {
      this.setHoverEvent()
    }

    this.selected && this.element.classList.remove('--selected')

    this.disabled && this.disable()

    return true
  }

  /**
   * Save the annotation data.
   */
  save () {
    window.annotationContainer.add(this)
  }

  /**
   * Delete the annotation from rendering, a container in window, and a container in localStorage.
   */
  destroy () {
    this.deleted = true
    this.element.remove()

    const promise = Promise.resolve()

    if (this.uuid) {
      window.annotationContainer.remove(this)
    }

    return promise
  }

  /**
   * Handle a click event.
   */
  handleSingleClickEvent (e: Event) {
    if (!this.selected) {
      this.toggleSelect()
    }

    if (this.selected) {
      // TODO Use common function.
      const event = document.createEvent('CustomEvent')
      event.initCustomEvent('annotationSelected', true, true, this)
      this.element.dispatchEvent(event)
    }
  }

  /**
   * Handle a hoverIn event.
   */
  handleHoverInEvent (e) {
    console.log('handleHoverInEvent')
    this.highlight()
    this.emit('hoverin')
    dispatchWindowEvent('annotationHoverIn', this)
  }

  /**
   * Handle a hoverOut event.
   */
  handleHoverOutEvent (e) {
    console.log('handleHoverOutEvent')
    this.dehighlight()
    this.emit('hoverout')
    dispatchWindowEvent('annotationHoverOut', this)
  }

  /**
   * Highlight the annotation.
   */
  highlight () {
    this.element.classList.add('--hover')
  }

  /**
   * Dehighlight the annotation.
   */
  dehighlight () {
    this.element.classList.remove('--hover')
  }

  /**
   * Select the annotation.
   */
  select () {
    this.selected = true
    this.selectedTime = Date.now()
    this.element.classList.add('--selected')
  }

  /**
   * Deselect the annotation.
   */
  deselect () {
    console.log('deselect')
    this.selected = false
    this.selectedTime = null
    this.element.classList.remove('--selected')
  }

  /**
   * Toggle the selected state.
   */
  toggleSelect () {
    if (this.selected) {
      this.deselect()
    } else {
      this.select()
    }
  }

  /**
   * Delete the annotation if selected.
   */
  deleteSelectedAnnotation (): boolean {
    if (this.isSelected()) {
      this.destroy().then(() => {
        dispatchWindowEvent('annotationDeleted', { uuid: this.uuid })
      })
      return true
    }
    return false
  }

  /**
   * Check whether the annotation is selected.
   */
  isSelected () {
    return this.element.classList.contains('--selected')
  }

  /**
   * Create a dummy DOM element for the timing that a annotation hasn't be specified yet.
   */
  createDummyElement (): HTMLElement {
    const element = document.createElement('dummy')
    element.classList.add('dummy')
    return element
  }

  /**
   * Get the central position of the boundingCircle.
   */
  getBoundingCirclePosition () {
    const knob = this.element.querySelector('.anno-knob') as HTMLElement
    if (!knob) {
      return null
    }

    return {
      x: parseFloat(knob.style.left) + DEFAULT_RADIUS / 2.0,
      y: parseFloat(knob.style.top) + DEFAULT_RADIUS / 2.0
    }
  }

  /**
   * Enable a view mode.
   */
  enableViewMode () {
    this.render()
  }

  /**
   * Disable a view mode.
   */
  disableViewMode () {
    this.render()
  }

  setDisableHoverEvent () {
    this.hoverEventDisable = true
  }

  setEnableHoverEvent () {
    this.hoverEventDisable = false
  }

  enable () {
    this.disabled = false
    this.element.style.pointerEvents = 'auto'
  }

  disable () {
    this.disabled = true
    this.element.style.pointerEvents = 'none'
  }

  /**
   * Check the another annotation is equal to `this`.
   */
  abstract equalTo(anotherAnnotation): boolean;

  /**
   * Check the argument is an annotation.
   */
  static isAnnotation (obj) {
    return obj && obj.uuid && obj.type
  }
}
