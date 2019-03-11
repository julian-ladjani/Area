import {AbstractManager} from '../abstract-manager';
import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ConnectionService} from '../../../services/connection.service';
import {ActionService} from '../../../services/action.service';
import {Connection} from '../../../objects/connection';
import {FormBuilder, Validators} from '@angular/forms';
import {StepsService} from '../../../services/steps.service';
import {AccountService} from '../../../services/account.service';
import {StructureService} from '../../../services/structure.service';
import {Option} from '../../../objects/option';

@Component({
    selector: 'app-account-manager',
    templateUrl: './account-manager.component.html',
    styleUrls: ['./account-manager.component.scss']
})
export class AccountManagerComponent extends AbstractManager implements OnInit {
    public test: '';
    private templateAccount: { description: string; options: Option[]; output: any; accountType: string };

    constructor(as: ActionService, ss: StepsService, public accountService: AccountService, public structureService: StructureService) {
        super(as, ss);
    }

    ngOnInit() {
        this.receiveActionUpdate();
        this.initManager();
//        this.accountService.getAccounts(this.structureService.getActionsTypes(this.action)['actionType']);
    }

    receiveActionUpdate() {
        this.updateTemplate();
    }

    updateTemplate() {
        const newTemplate = this.structureService.getActionTemplate(this.action);
        if (this.templateAccount !== newTemplate)
            this.accountService.getAccounts(this.templateAccount ? this.templateAccount.accountType : null);
        this.templateAccount = newTemplate;
    }

    get accounts() {
        this.updateTemplate();
        if (this.templateAccount)
            console.log(this.templateAccount.accountType);
        if (this.templateAccount && this.templateAccount.accountType)
            return this.accountService.accounts;
        return [];
    }

    getFormGroup() {
        if (!this.action.account)
            this.action.account = {id: '', type: ''};
        return {
            accountControl: [this.action.account, Validators.required]
        };
    }

}
