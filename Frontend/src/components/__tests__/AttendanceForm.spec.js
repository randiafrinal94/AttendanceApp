import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import AttendanceForm from '../AttendanceForm.vue'
import api from '../../libs/axios'

// Mock the axios instance
vi.mock('../../libs/axios', () => ({
    default: {
        post: vi.fn()
    }
}))

describe('AttendanceForm', () => {
    it('renders correctly', () => {
        const wrapper = mount(AttendanceForm)
        expect(wrapper.text()).toContain('Attendance Action')
        expect(wrapper.find('input#employeeId').exists()).toBe(true)
        expect(wrapper.findAll('button')).toHaveLength(2)
    })

    it('shows error if employee ID is empty on checkin', async () => {
        const wrapper = mount(AttendanceForm)
        // Find the Check In button (green one)
        const checkInBtn = wrapper.findAll('button')[0]
        await checkInBtn.trigger('click')
        expect(wrapper.text()).toContain('Employee ID is required')
    })

    it('calls api on checkin with valid ID', async () => {
        const wrapper = mount(AttendanceForm)

        // Mock successful response
        const mockDate = new Date('2023-01-01T10:00:00')
        api.post.mockResolvedValue({
            data: {
                checkInTime: mockDate.toISOString()
            }
        })

        const input = wrapper.find('input#employeeId')
        await input.setValue(123)

        const checkInBtn = wrapper.findAll('button')[0]
        await checkInBtn.trigger('click')

        expect(api.post).toHaveBeenCalledWith('/api/attendance/checkin', { employeeId: 123 })

        // Wait for DOM update
        await wrapper.vm.$nextTick()
        // Small delay for async operations in component
        await new Promise(resolve => setTimeout(resolve, 10))

        expect(wrapper.text()).toContain('Success!')
    })
})
