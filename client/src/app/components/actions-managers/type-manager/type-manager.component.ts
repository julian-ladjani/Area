import {Component, Input, OnInit} from '@angular/core';
import {AreaService} from '../../../services/area.service';
import {StructureService} from '../../../services/structure.service';
import {AbstractManager} from '../abstract-manager';
import {ActionService} from '../../../services/action.service';
import {FormBuilder, FormArray, Validators, FormControl} from '@angular/forms';
import {StepsService} from '../../../services/steps.service';

@Component({
    selector: 'app-service-manager',
    templateUrl: './type-manager.component.html',
    styleUrls: ['./type-manager.component.scss']
})

export class TypeManagerComponent extends AbstractManager implements OnInit{

    constructor(as: ActionService, ss: StepsService, private areaService: AreaService,
                private structureService: StructureService) {
        super(as, ss);
    }

    public services: string[];

    getFormGroup() {
        return {
            typeControl: [this.action.serviceName, Validators.required]
        };
    }

    ngOnInit() {
        this.initManager();
        this.services = this.structureService.getServices();
    }

    isTypeSelected(type) {
        return this.action && type === this.action.serviceName;
    }

    setSelectedType(type) {
        this.action.serviceName = type;
        this.emitActionUpdate();
    }

    receiveActionUpdate() {
    }
}
